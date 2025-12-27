#!/bin/bash

x=1
y=2

if [ $x -eq $y ]; then
	echo "equal"
else
	echo "not equal"
fi

Echo "end of test"

CIDR_BLOCK="10.0.0.0/16"
AWS_REGION="us-east-2"
SUBNET_CIDR_BLOCK="10.0.1.0/24"

# create VPC
VPC_ID=$(aws ec2 create-vpc --cidr-block $CIDR_BLOCK --region $AWS_REGION --query "Vpc.VpcId" --output text)
echo "VPC created id: $VPC_ID"

# create Subnet
SUBNET_ID=$(aws ec2 create-subnet --vpc-id "$VPC_ID" --cidr-block "$SUBNET_CIDR_BLOCK" --region $AWS_REGION --query "Subnet.SubnetId" --output text)
echo "SUBNET created id: $SUBNET_ID"

# create Route Table
ROUTE_TABLE_ID=$(aws ec2 create-route-table --vpc-id $VPC_ID --region $AWS_REGION --query "RouteTable.RouteTableId" --output text)
echo "ROUTE TABLE created id: $ROUTE_TABLE_ID"

# create Internet Gateway for above created route table
INTERNET_GATEWAY_ID=$(aws ec2 create-internet-gateway --region $AWS_REGION --query "InternetGateway.InternetGatewayId" --output text)
echo "INTERNET GATEWAY created id: $INTERNET_GATEWAY_ID"

# attach Internet Gateway to VPC
$(aws ec2 attach-internet-gateway --vpc-id "$VPC_ID" --internet-gateway-id $INTERNET_GATEWAY_ID --region $AWS_REGION)
echo "Internet gateway attached to vpc"

# create internet route
RT_ID=$(aws ec2 create-route --route-table-id $ROUTE_TABLE_ID --destination-cidr-block "0.0.0.0/0" --gateway-id $INTERNET_GATEWAY_ID --region $AWS_REGION --query "RouteTable.RouteTableId" --output text)
echo "internet route created"

# create local route
# $(aws ec2 create-route --route-table-id $ROUTE_TABLE_ID --destination-cidr-block $CIDR_BLOCK --gateway-id "local" --region $AWS_REGION)
echo "skipping local route created"

# associate route table to subnet -- this removes default association to main vpc route table
ASSOCIATION_ID=$(aws ec2 associate-route-table --subnet-id $SUBNET_ID --route-table-id $ROUTE_TABLE_ID --region $AWS_REGION --query "AssociationId" --output text)
echo "route table associated to subnet"

# create security group
SECURITY_GROUP_ID=$(aws ec2 create-security-group --vpc-id $VPC_ID --region $AWS_REGION --description "test security group" --group-name "test-sg" --query "GroupId" --output text)
echo "security group created"

# create ingress rules
RULE_ID=$(aws ec2 authorize-security-group-ingress --group-id $SECURITY_GROUP_ID --protocol tcp --port 22 --cidr 0.0.0.0/0 --region $AWS_REGION --query "SecurityGroupRules.SecurityGroupRuleId" --output text)
echo "ingress rule created"

# create egress rules
# no need to create, should exist by default
echo "egress rule skipped"

# create instance 
INSTANCE_ID=$(aws ec2 run-instances --image-id  ami-00e428798e77d38d9 --subnet-id $SUBNET_ID --security-group-ids $SECURITY_GROUP_ID --key-name /Users/nrazdhan/PEMkey/nrazdhan2keypair.pem --instance-type t3.micro --region $AWS_REGION --query "Instances[0].InstanceId" --output text)
echo "instance created id: $INSTANCE_ID"



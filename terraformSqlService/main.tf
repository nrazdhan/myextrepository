terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.6.0"
    }
  }
  required_version = ">=1.0.0"
}

provider "aws" {
  region = "us-east-2"
}

resource "aws_vpc" "myVpc1" {
  cidr_block           = "172.0.0.0/16"
  enable_dns_hostnames = true
  enable_dns_support   = true

  tags = {
    Name = "myVpc1"
  }
}

resource "aws_internet_gateway" "myInternetGatewayVpc1" {
  vpc_id = aws_vpc.myVpc1.id

  tags = {
    Name = "myInternetGatewayVpc1"
  }
}

resource "aws_subnet" "mySqlSubnet" {
  vpc_id                  = aws_vpc.myVpc1.id
  cidr_block              = "172.0.1.0/24"
  availability_zone       = "us-east-2a"
  map_public_ip_on_launch = true

  tags = {
    Name = "mySqlSubnet"
  }
}

resource "aws_route_table" "myRouteTableSqlSubnet" {
  vpc_id = aws_vpc.myVpc1.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.myInternetGatewayVpc1.id
  }

  tags = {
    Name = "myRouteTableSqlSubnet"
  }
}

resource "aws_route_table_association" "mySqlSubnetRouteTableAssociation" {
  subnet_id  = aws_subnet.mySqlSubnet.id
  route_table_id=aws_route_table.myRouteTableSqlSubnet.id
}

resource "aws_security_group" "mySqlSecurityGroup" {
  vpc_id = aws_vpc.myVpc1.id
  name   = "mySqlSecurityGroup"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = -1
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "mySqlSecurityGroup"
  }
}

data "aws_ami" "mySqlAmi" {
  most_recent = true
  name_regex  = "mySqlAMI"
  owners      = ["182643220141"]
}

resource "aws_instance" "mySqlInstance" {
  ami                        = data.aws_ami.mySqlAmi.id
  instance_type              = "t2.micro"
  subnet_id                  = aws_subnet.mySqlSubnet.id
  security_groups            = [aws_security_group.mySqlSecurityGroup.id]
  associate_public_ip_address = true
  key_name                   = "nrazdhan2keypair"


  tags = {
    Name = "mySqlInstance"
  }

}
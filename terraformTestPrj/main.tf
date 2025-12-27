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

data "aws_ami" "ngxImg1" {
  most_recent = true
  name_regex  = "al2023-ami-2023.9.20251208.0-kernel-6.1-x86_64"
  owners      = ["137112412989"]
}

resource "aws_vpc" "myVpc1" {
  cidr_block = "172.0.0.0/24"

  tags = {
    Name = "myVpc1Name"
  }
}

resource "aws_internet_gateway" "myIngw" {
  vpc_id = aws_vpc.myVpc1.id

  tags = {
    Name = "myIgwVpc1"
  }
}

resource "aws_route_table" "mySubnetA_Route_Table" {
  vpc_id = aws_vpc.myVpc1.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.myIngw.id
  }
  tags = {
    Name = "subnetARouteForVpc1"
  }
}

resource "aws_subnet" "mySubnetA" {
  vpc_id            = aws_vpc.myVpc1.id
  cidr_block        = "172.0.0.16/28"
  availability_zone = "us-east-2a"

  tags = {
    Name = "mySubnetA_On_myVpc1_in_2aZone"
  }
}

resource "aws_route_table_association" "myRTSUB_Association" {
  subnet_id      = aws_subnet.mySubnetA.id
  route_table_id = aws_route_table.mySubnetA_Route_Table.id
}

resource "aws_security_group" "ngxSg1" {
  name        = "ngxSg1Name"
  description = "inbound and outbound rules for nginx instance"

  ingress {
    from_port   = 8081
    to_port     = 8081
    cidr_blocks = ["0.0.0.0/0"]
    protocol    = "tcp"
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = -1
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name    = "ngsSg1Name"
    someTag = "ngsSg1TagName"
  }
}

resource "aws_instance" "ngxInstance1" {
  ami                    = data.aws_ami.ngxImg1.id
  instance_type          = "t2.micro"
  vpc_security_group_ids = [aws_security_group.ngxSg1.id]

  tags = {
    Name    = "ngxInstance1Tag"
    someTag = "ngxCustomTag"
  }
}
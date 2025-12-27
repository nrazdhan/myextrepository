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

data "aws_ami" "myAwsAmi" {
  most_recent = true
  name_regex  = "myHelloServiceAMI2"
  // "al2023-ami-2023.9.20251208.0-kernel-6.1-x86_64"
  owners = ["182643220141"]
  //["137112412989"]
}

resource "aws_instance" "myHelloInstance" {
  ami           = data.aws_ami.myAwsAmi.id
  instance_type = "t2.micro"
  key_name      = "nrazdhan2keypair"

  tags = {
    Name = "myHelloInstance"
  }
}


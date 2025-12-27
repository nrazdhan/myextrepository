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

/*
data "template_file" "app_env_template" {
  template = file("../app.env")
  vars = {
    JDBC_URL = "http://Naveen.Razdhan.com:3360/naveen"
  }
}
*/

data "aws_ami" "myRequiredAmi" {
  most_recent = true
  name_regex  = "al2023-ami-2023.9.20251208.0-kernel-6.1-x86_64"
  owners      = ["137112412989"]
}


resource "aws_instance" "myNginxInstanceForImageCreation" {
  ami           = data.aws_ami.myRequiredAmi.id
  instance_type = "t2.micro"
  key_name      = "nrazdhan2keypair"

  /*
  provisioner "file" {
    content     = data.template_file.app_env_template.rendered
    destination = "/home/ec2-user/HelloWorld/app.env"
    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = file("~/PEMkey/nrazdhan2keypair.pem")
      host        = self.public_ip
    }
  }
*/

  tags = {
    Name = "myNginxInstanceForImageCreation"
  }
}
x=10
y=20
print("sum =", x + y)
z=input("what is your name:");
zz=input("how old are you?");
yy=input("where do you live?");
print("Hello " + z + ", you are " + zz + " years old and you live in " + yy + ".")

number = int(input("Enter a number: "))
if(number % 2 == 0):
    print("The number is even.")
else:
    print("The number is odd.")

r=int(input("Enter a number to calculate its factorial: "))

def factorial(n):
    if n == 0 or n == 1:
        return 1
    else:
        return n * factorial(n - 1)
    
factorial_result = factorial(r)
print("The factorial of", r, "is", factorial_result)    

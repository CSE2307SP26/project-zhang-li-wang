# project26

## Team Members:

* Tianwei Wang
* Da Li
* Tiancheng Zhang
  

## User stories

Iteration1:
1. A bank customer should be able to deposit into an existing account. (Shook)
2. A bank customer should be able to withdraw from an account.(Tiancheng Zhang) 
3. A bank customer should be able to check their account balance. (Tianwei Wang)
4. A bank customer should be able to create an additional account with the bank. (Da Li)
5. A bank customer should be able to close an existing account.(Tiancheng Zhang)
6. A bank customer should be able to transfer money from one account to another. (Da Li)
7. A bank adminstrator should be able to add an interest payment to an existing account when necessary. (Tianwei Wang)


Iteration2:
1. A bank adminstrator should be able to collect fees from existing accounts when necessary.(Tianwei Wang)
2. A user should be able to set a 4-digit PIN for their account. (Tianwei Wang)
3. A bank customer should be able to view a summary of their account including balance, interest rate, and whether a PIN is set. (Tiancheng Zhang)
4. A bank customer should be able to receive an error message when performing an invalid operation. (Tiancheng Zhang)
5. A bank customer should be able to view their transaction history for an account. (Da Li)
6. A bank customer should be able to schedule recurring bill payments. (Da Li)

Iteration3:
1. A user should be able to use a 4-digit PIN to protect their account operations. (Tianwei Wang)
2. A bank administrator should be able to freeze or unfreeze an account. （Tianwei Wang）
3. A bank customer should receive alerts for activities like low balance.(Da Li)
4. A bank customer should be able to enable overdraft protection on their account, allowing limited negative balances with fees, and receive warnings when approaching the limit. (Da Li)
5. A bank customer should be able to calculate a loan monthly payment based on principal, APR, and term (Tiancheng Zhang)
6. A bank customer should be able to estimate how long it takes to reach a savings goal with fixed monthly deposits. (Tiancheng Zhang)


## What user stories do you intend to complete next iteration?
None. Final iteration.

## Is there anything that you implemented but doesn't currently work?

All the tests we conducted passed successfully.

## What commands are needed to compile and run your code from the command line?
To compile and run the program, simply run runApp.sh: bash runApp.sh

From the src directory, run:

javac main/*.java test/*.java

java main.MainMenu

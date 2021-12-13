# rabobank-assignment

you can see the structure of the application on this url :
http://localhost:8083/app/swagger-ui.html

---------------------------------------------------------------
try it out:

1) first you need to save a user by adding a Id and a name.
2) then you can create an account and account's holder name has a reference from user table.
  note: for adding the type of account use  "0" : saving account and "1" : paymentaccount
3) at the end adding a power of attorney can be done.
  note: the grantor and the grantee both has a reference from user table and you need to put a user Id for both of them
  note: for authorization the applicable values are "READ" and "WRITE".

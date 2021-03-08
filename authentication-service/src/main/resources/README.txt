POST /auth/signUp 				                    -> Sign Up (Create user for app)
GET  /auth/signIn 				                    -> Sign In
GET /auth/confirmAccount 		                    -> Mail verification. As a response, bearer token is taken for authentication to other services
								                    
PUT /quotes/updateAll 			                    -> Update all quotes from IEX api
PUT /quotes/          			                    -> Update quote with user object
POST /quotes/tickerId/{tickerId} 	                -> Create quote with IEX api Symbol
GET /quotes/tickerId/{tickerId} 	                -> Get quote with IEX api Symbol
GET /quotes/					                    -> Get all quotes
								                    
POST /orders/					                    -> Place an order (Buy-Sell)
Ex. Json

{
    "accountId" : 2,
    "size" : 25,
    "ticker" : "ABC",
    "orderType" : "BUY"
}
								                    
POST /traders/					                    -> Create Trader User (For using trade operations)
DELETE /traders/{traderId}		                    -> Delete Trader User
PUT /traders/{traderId}/deposit/amount/{amount}		-> Deposit to account
PUT /traders/{traderId}/withdraw/amount/{amount}	-> withdraw from account
GET /traders/{traderId}/profile						-> Account Info

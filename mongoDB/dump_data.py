import json
from pymongo import MongoClient 
from dotenv import load_dotenv
import os
 
load_dotenv()
 
# Making Connection
 
username = os.getenv('MONGO_INITDB_ROOT_USERNAME')
password = os.getenv('MONGO_INITDB_ROOT_PASSWORD')
port="27017"

myclient = MongoClient("mongodb://localhost:"+port+"/",username=username,password=password)

# database 
db = myclient["jui_db"]

Collection = db["product-catalogue"]
 
# Loading or Opening the json file
with open('sd.json') as file:
    file_data = json.load(file)


Collection.insert_many(file_data)  

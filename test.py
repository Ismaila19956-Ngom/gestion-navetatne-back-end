import urllib.request
import json

url = 'https://app.paydunya.com/sandbox-api/v1/checkout-invoice/create'
headers = {
    'PAYDUNYA-MASTER-KEY': 'ClgaecKt-jHO8-xLS8-6E6J-s3vjYZr9nIRV',
    'PAYDUNYA-PRIVATE-KEY': 'test_private_tNBclYu7Q75GomBCvKBIcySLRqE',
    'PAYDUNYA-TOKEN': 'gcnLJ1DYfKhJdCUxmp0K',
    'Content-Type': 'application/json'
}
data = {
    "invoice": {
        "total_amount": 1000,
        "description": "Test Invoice"
    },
    "store": {
        "name": "Navetane App"
    }
}
req = urllib.request.Request(url, json.dumps(data).encode('utf-8'), headers)
try:
    response = urllib.request.urlopen(req)
    print(response.read().decode('utf-8'))
except Exception as e:
    print(e.read().decode('utf-8') if hasattr(e, 'read') else str(e))

curl --location 'http://localhost:9000/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'client_id=football' \
--data-urlencode 'client_secret=SuperSecret' \
--data-urlencode 'scope=football:read'


curl --location 'http://localhost:9000/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'client_id=football' \
--data-urlencode 'client_secret=SuperSecret' \
--data-urlencode 'scope=football:admin'

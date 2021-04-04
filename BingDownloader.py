import requests

url = 'https://www.bing.com/'
html = requests.get(url)
stream = str(html.content)

lower = stream.index('href="/th?id=')
upper = stream.index('"', lower+10)

img_url = url + stream[lower+6:upper]

img_bytes = requests.get(img_url)
open("background", 'wb').write(img_bytes.content)
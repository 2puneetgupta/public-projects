from urllib2 import Request, urlopen
import json

import pandas as pd

path1 = '42.974049,-81.205203|42.974298,-81.195755'
request=Request('http://maps.googleapis.com/maps/api/elevation/json?locations='+path1+'&sensor=false')
response = urlopen(request)
elevations = response.read()
data = json.loads(elevations)
df = pd.json_normalize(data['results'])
# simple-data-warehouse
Simple backend application that exposes data via an API.


### Task description

You are going to write a simple backend application that exposes data - extracted from a csv file - via an API.
The file can be found here:

- http://adverity-challenge.s3-website-eu-west-1.amazonaws.com/PIxSyyrIKFORrCXfMYqZBI.csv

Transform it to your needs, so it can be loaded into your favorite data store.

The API should make it possible to query the data in a generic and efficient way.
Possible queries might look like this:

- Total Clicks for a given Datasource for a given Date range
- Click-Through Rate (CTR) per Datasource and Campaign
- Impressions over time (daily)

And the data looks like this:

- a time dimension (Date)
- regular dimensions (Campaign, Datasource)
- metrics(Clicks,Impressions)

As a hint, the API consumes these parameters

- a set of metrics (plus calculated ones) to be aggregated on
- an optional set of dimensions to be grouped by
- an optional set of dimension filters to be filtered on

#### Tech / architecture

Feel free to
- Choose any JVM based language and framework
- Design any architecture that fits the problem/usecase best
- Model the API contract on your behalf
- Use any additional tech that helps extracting, transforming, loading and querying data
Deliverable
- Publish your solution on GitHub
- Deploy it somewhere so that the API can be publicly queried from


### Solution

The solution is build top of Spring Boot 2.3: JPA Repositories/Spring Rest Data (HATEAOS). 
The interfaces of API have been documented. The statistic aggregates has been implemented with simple RestController and documented with [Swagger UI](https://simple-data-warehouse-293620.oa.r.appspot.com/swagger-ui/index.html?configUrl=/api-docs/swagger-config).
The Spring JPA Repositories give us a REST API for CRUD operations (create, read, update, delete) with own [documentation UI](https://simple-data-warehouse-293620.oa.r.appspot.com/warehouse/rest/explorer/index.html#uri=/warehouse/rest/statistics).

_You can see the working API through examples._

##### Total Clicks for a given Datasource for a given Date range
- Request: https://simple-data-warehouse-293620.oa.r.appspot.com/api/statistics/aggregates/ds/Google%20Ads/2019-10-01/2019-12-31
- Response 

```JSON
{
  "datasource": "Google Ads",
  "clicks": 15000,
  "impressions": 7855578,
  "clickThroughRate": 0.19094712063198915
}
```

##### Click-Through Rate (CTR) per Datasource and Campaign
- Request: https://simple-data-warehouse-293620.oa.r.appspot.com/api/statistics/aggregates/ds-cp/Twitter%20Ads/Versicherungen/2020-01-01/2020-03-31
- Response 

```JSON
{
  "datasource": null,
  "campaign": "Versicherungen",
  "clicks": 1883,
  "impressions": 17468,
  "clickThroughRate": 10.779711472406687
}
```

##### Impressions over time (daily)

- Request: https://simple-data-warehouse-293620.oa.r.appspot.com/api/statistics/aggregates/daily/ds-cp/Twitter%20Ads/Versicherungen/2020-01-01/2020-01-31
- Response 

```JSON
[
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-01",
    "clicks": 28,
    "impressions": 228,
    "clickThroughRate": 12.280701754385966
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-02",
    "clicks": 44,
    "impressions": 400,
    "clickThroughRate": 11
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-03",
    "clicks": 31,
    "impressions": 352,
    "clickThroughRate": 8.806818181818182
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-04",
    "clicks": 37,
    "impressions": 276,
    "clickThroughRate": 13.405797101449275
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-05",
    "clicks": 33,
    "impressions": 325,
    "clickThroughRate": 10.153846153846153
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-06",
    "clicks": 44,
    "impressions": 379,
    "clickThroughRate": 11.609498680738787
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-07",
    "clicks": 35,
    "impressions": 325,
    "clickThroughRate": 10.76923076923077
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-08",
    "clicks": 39,
    "impressions": 661,
    "clickThroughRate": 5.900151285930408
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-09",
    "clicks": 42,
    "impressions": 521,
    "clickThroughRate": 8.061420345489443
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-10",
    "clicks": 37,
    "impressions": 418,
    "clickThroughRate": 8.851674641148325
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-11",
    "clicks": 35,
    "impressions": 273,
    "clickThroughRate": 12.820512820512821
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-12",
    "clicks": 38,
    "impressions": 305,
    "clickThroughRate": 12.459016393442623
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-13",
    "clicks": 42,
    "impressions": 453,
    "clickThroughRate": 9.271523178807946
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-14",
    "clicks": 46,
    "impressions": 446,
    "clickThroughRate": 10.31390134529148
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-15",
    "clicks": 46,
    "impressions": 434,
    "clickThroughRate": 10.599078341013826
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-16",
    "clicks": 34,
    "impressions": 319,
    "clickThroughRate": 10.658307210031348
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-17",
    "clicks": 43,
    "impressions": 303,
    "clickThroughRate": 14.191419141914192
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-18",
    "clicks": 32,
    "impressions": 226,
    "clickThroughRate": 14.15929203539823
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-19",
    "clicks": 32,
    "impressions": 231,
    "clickThroughRate": 13.852813852813853
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-20",
    "clicks": 49,
    "impressions": 406,
    "clickThroughRate": 12.068965517241379
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-21",
    "clicks": 45,
    "impressions": 364,
    "clickThroughRate": 12.362637362637363
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-22",
    "clicks": 47,
    "impressions": 363,
    "clickThroughRate": 12.947658402203857
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-23",
    "clicks": 47,
    "impressions": 407,
    "clickThroughRate": 11.547911547911548
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-24",
    "clicks": 41,
    "impressions": 369,
    "clickThroughRate": 11.11111111111111
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-25",
    "clicks": 36,
    "impressions": 287,
    "clickThroughRate": 12.543554006968641
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-26",
    "clicks": 33,
    "impressions": 268,
    "clickThroughRate": 12.313432835820896
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-27",
    "clicks": 40,
    "impressions": 388,
    "clickThroughRate": 10.309278350515465
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-28",
    "clicks": 46,
    "impressions": 331,
    "clickThroughRate": 13.897280966767372
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-29",
    "clicks": 43,
    "impressions": 455,
    "clickThroughRate": 9.45054945054945
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-30",
    "clicks": 36,
    "impressions": 352,
    "clickThroughRate": 10.227272727272727
  },
  {
    "datasource": "Twitter Ads",
    "campaign": "Versicherungen",
    "daily": "2020-01-31",
    "clicks": 45,
    "impressions": 443,
    "clickThroughRate": 10.15801354401806
  }
]
```

#### DEMO

https://simple-data-warehouse-293620.oa.r.appspot.com/

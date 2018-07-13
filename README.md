# Piping Search Plugin for elasticsearch
> Provide another searching method with piping and post processing of data
> Provide Splunk like searching query to simplify searching, support column rename, value eval, cross index data join, etc.

## Usage
Install plugin in Elasticsearch 6.3.1
  - `bin/elasticsearch-plugin install [url]`

Install plugin [kibana-piping-search-plugin](https://github.com/kkeithz/kibana-piping-search-plugin) in kibana 6.3.1

## Screenshot from kibana plugin
![Screenshot1](https://github.com/kkeithz/kibana-piping-search-plugin/blob/master/screenshot/screenshot1.png?raw=true)
![Screenshot2](https://github.com/kkeithz/kibana-piping-search-plugin/blob/master/screenshot/screenshot2.png?raw=true)

## Searching Query
Example searching query
 - `search index=* field=count(ip) group=ip | table field=ip,count_ip`

Support action
 - `search index=[index1,index2] field=[field_name]`
 - `search index=[index1,index2] field=[agg_field] group=[field_name/datehistogram(@timestamp,1h)]`
 - `eval [new_field_name]=[expression]`
 - `rename [new_field_name]=[field]`
 - `sort [field]=['asc'|'desc']`
 - `table field=[field1,field2]`
 - `join on=[field1,field2] [search ... | ...]`


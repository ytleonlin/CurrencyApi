# Currency API #
## Database ##
* Table schema in file: `src/main/resources/db/schema.sql`

## Web API ##
### Coin Desk ###
* `GET coin-desk/original`
  * Response:
    * Content-Type: `application/json`
    * Body: the original data from CoinDesk website.
* `GET coin-desk/transferred`
  * Request:
    * Parameters:
      * `language` (optional): `zh-TW` as default value.
  * Response:
    * Content-Type: `application/json`
    * Body: the data from CoinDesk website and add fields:
      1. `updatedDisplay`: update time format
      2. `codeDisplay`: the currency name in specific language
      3. `codeDisplayLanguage`: the language name of `codeDisplay`
    * Body example:
      ```json
      {
          "time": {
              "updatedISO": "2022-09-22T10:45:00Z",
              "updatedDisplay": "2022/09/22 18:45:00"
          },
          "chartName": "Bitcoin",
          "bpi": {
              "USD": {
                  "code": "USD",
                  "rate": "19,132.8787",
                  "codeDisplay": "美元",
                  "codeDisplayLanguage": "zh-TW",
                  "rate_float": 19132.879
              },
              "GBP": {
                  "code": "GBP",
                  "rate": "15,987.2803",
                  "codeDisplay": "英鎊",
                  "codeDisplayLanguage": "zh-TW",
                  "rate_float": 15987.28
              },
              "EUR": {
                  "code": "EUR",
                  "rate": "18,638.2172",
                  "codeDisplay": "歐元",
                  "codeDisplayLanguage": "zh-TW",
                  "rate_float": 18638.217
              }
          }
      }
      ```

### Currency Display ###
* Note: `code` + `language` is unique. 
* `POST currency-displays`
  * Request:
    * Content-Type: `application/json`
    * Body: should not have id
      ```json
      {"code":"GBP","display":"英鎊","language":"zh-TW"}
      ```
  * Response:
    * Content-Type: `application/json`
    * Status: 201 if create successfully
    * Body:
      ```json
      {"id":51,"code":"GBP","display":"英鎊","language":"zh-TW"}
      ```
* `GET currency-displays/id/{id}`
  * Request:
    * `id`: the id of CurrencyDisplay
  * Response:
    * Body:
      ```json
      {"id":51,"code":"GBP","display":"英鎊","language":"zh-TW"}
      ```
* `GET currency-displays/code/{code}`
  * Request:
    * `code`: the currency code of CurrencyDisplay 
  * Response:
    * Body:
      ```json
      {"id":51,"code":"GBP","display":"英鎊","language":"zh-TW"}
      ```
* `PATCH currency-displays/id/{id}`
  * Request:
    * `id`: the id of CurrencyDisplay
    * Content-Type: `application/json`
    * Body: a json object with fields: `code`, `display` or `language`
      ```json
      {"code":"GBP","display":"英鎊","language":"zh-TW"}
      ```
  * Response:
    * Body: the updated CurrencyDisplay
      ```json
      {"id":51,"code":"GBP","display":"英鎊","language":"zh-TW"}
      ```
* `DELETE currency-displays/id/{id}`
  * Request:
    * `id`: the id of CurrencyDisplay
  * Response:
    * Status: 204 if delete successfully

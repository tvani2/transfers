# transfer
პროექტი წარმოადგენს `Transfers` აპლიკაციას, რომელიც უზრუნველყოფს ტრანსფერების ოპტიმიზაციას მოცულობისა და ფასის მიხედვით.

## აღწერა
პროექტი წარმოადგენს API-ს, რომელიც მუშაობს ტრანსფერების მონაცემებზე. აპლიკაცია იღებს ტრანსფერების სიის მონაცემებს, თითოეული ტრანსფერის წონასა და ღირებულებას, და ითვლის ოპტიმალურ ტრანსფერებს მაქსიმალური წონის მიხედვით. 

## ფუნქციონალი
- **Input**:
    - წონა და ღირებულება JSON ფორმატში
    - მაქსიმალური წონის შეზღუდვა
- **output**:
    - ოპტიმალური ტრანსფერების სია
    - შერჩეული ტრანსფერების რაოდენობა

## ინსტალაცია
### საჭირო პრერეკვიზიტები

აპლიკაციის გაშვების წინ, დარწმუნდით რომ გაქვთ:
- Java 11+
- [Maven](https://maven.apache.org/)
- [Spring Boot](https://spring.io/projects/spring-boot) 

### აპლიკაციის გასაშვებად გაუშვით ბრძანებები

git clone https://github.com/tvani2/transfers.git
cd transfers
mvn clean install
mvn spring-boot:run

## ტესტები
პროექტში შედის ტესტები, რომლებიც სხვადასხვა ტრანსფერის რაოდენობისა და საერთო ღირებულების/ წონის მიხედვით ითვლის შედეგებს. 

API Endpoints
POST /api/transfers/calculate
ეს endpoint ითვლის ოპტიმალურ ტრანსფერებს, მაქსიმალური წონის ლიმიტის მიხედვით.
{
  "maxWeight": 15,
  "availableTransfers": [
    { "weight": 5, "cost": 10 },
    { "weight": 10, "cost": 20 },
    { "weight": 3, "cost": 5 },
    { "weight": 8, "cost": 15 }
  ]
}

maxWeight: მაქსიმალური წონის ლიმიტი ტრანსფერებისთვის.
availableTransfers: ხელმისაწვდომი ტრანსფერების სია, სადაც თითოეული ტრანსფერი შეიცავს weight და cost.
პასუხი:
{
  "totalCost": 30,
  "totalWeight": 15,
  "selectedTransfers": [
    { "weight": 5, "cost": 10 },
    { "weight": 10, "cost": 20 }
  ]
}

totalCost: არჩეულ ტრანსფერების საერთო ღირებულება.
totalWeight: არჩეულ ტრანსფერების საერთო წონა.
selectedTransfers: არჩეულ ტრანსფერების სია, რომლებიც შევა maxWeight ფარგლებში.

Example CURL Requests and Responses
მოთხოვნა:
{
  "maxWeight": 15,
  "availableTransfers": [
    { "weight": 5, "cost": 10 },
    { "weight": 10, "cost": 20 },
    { "weight": 3, "cost": 5 },
    { "weight": 8, "cost": 15 }
  ]
}'

პასუხი:
{
  "totalCost": 30,
  "totalWeight": 15,
  "selectedTransfers": [
    { "weight": 5, "cost": 10 },
    { "weight": 10, "cost": 20 }
  ]
}

## დამხმარე ტექნოლოგიები
- **Spring Boot**
- **JUnit**
- **MockMvc**

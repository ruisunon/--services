const jsonServer = require('json-server');
const server = jsonServer.create();
const router = jsonServer.router('db.json');
const middlewares = jsonServer.defaults();

server.use(middlewares);

server.post('/auth/login', (req, res) => {
  res.send({ token: 'SOME TOKEN' });
});

server.post('/sales', (req, res) => {
  const sale = {
    id: '75bd57d5-5ccb-44d3-a789-2036eafc4d13',
    date: '2022-01-02',
    price: 5,
    customer: {
      name: 'Fake Customer',
      email: 'customer@test.com',
      phone: '234223424'
    },
    seller: {
      id: 'seller-1',
      name: 'Raphael Collin',
      email: 'raphael@test.com',
      status: 'ACTIVE',
      joinDate: '2022-08-04',
      active: true
    },
    car: {
      id: 'e8cb425d-e9ba-46ff-9ef1-e360785fb8e2',
      name: 'HB20',
      brand: 'Hyundai',
      color: 'WHITE',
      status: 'ACTIVE',
      type: 'HATCH',
      chassis: '234234234',
      mileage: 60000,
      releaseYear: 2014,
      acquisition: {
        date: '2022-07-01',
        price: 35000,
        source: 'PRIVATE'
      },
      photos: [
        {
          description: 'Front',
          url: 'https://www.netcarshow.com/Hyundai-HB20S-2013-1600-0a.jpg'
        },
        {
          description: 'Back',
          url: 'https://www.netcarshow.com/Hyundai-HB20S-2013-1600-0b.jpg'
        }
      ],
      sold: false
    },
    profit: -34995
  };

  res.status(201).jsonp(sale);
});

server.use(router);

server.listen(3000, () => {
  console.log('JSON Server is running');
});

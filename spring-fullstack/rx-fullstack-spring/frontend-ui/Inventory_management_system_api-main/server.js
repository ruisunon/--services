require("dotenv").config();
const express = require("express");
const app = express();
const mongoose = require("mongoose");
const cors = require("cors");
const { requireAuth } = require("./middlewares/authMiddleware");
const productRouter = require("./routes/product-router");
const supplierRouter = require("./routes/supplier-router");
const materialRouter = require("./routes/material-router");
const productionItem = require("./routes/productionprocessitem-router");
const processRouter = require("./routes/productionprocess-router");
const authRouter = require("./routes/user-router");
const cookieParser = require("cookie-parser");

mongoose.connect(process.env.DATABASE_URL);

const db = mongoose.connection;
db.on("error", (error) => console.error(error));
db.once("open", () => console.log("Connected to Database"));
app.use(express.json());

//UTILS

app.use(cookieParser());
app.use(
  cors({
    origin: "http://localhost:5173",
    credentials: true,
    exposedHeaders: ["Set-Cookie", "Date", "ETag"],
  })
);
// app.use(function (req, res, next) {
//   res.header("Content-Type", "application/json;charset=UTF-8");
//   res.header("Access-Control-Allow-Credentials", true);
//   res.header(
//     "Access-Control-Allow-Headers",
//     "Origin, X-Requested-With, Content-Type, Accept"
//   );
//   next();
// });
// ROUTES
app.use("/products", requireAuth, productRouter);
app.use("/suppliers", requireAuth, supplierRouter);
app.use("/materials", requireAuth, materialRouter);
app.use("/processItem", requireAuth, productionItem);
app.use("/process", requireAuth, processRouter);
app.use("/auth", authRouter);

app.listen(3000, () => console.log("Server started"));

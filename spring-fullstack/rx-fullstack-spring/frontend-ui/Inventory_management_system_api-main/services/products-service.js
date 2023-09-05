// import Product from "../models/Product";
const Product = require("../models/Product");
const ProductionProcess = require("../models/ProductionProcess");
exports.getAllProducts = async (req, res) => {
  let products;
  try {
    products = await Product.find();
    return res.status(200).json({ products });
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
};

exports.createNewProduct = async (req, res) => {
  let returnItem;
  const { name, picUrl, profitMargin, productionProcess } = req.body;
  const newProduct = new Product({
    name,
    picUrl,
    profitMargin,
    productionProcess,
  });
  try {
    const foundProcess = await ProductionProcess.findById(productionProcess);
    if (!foundProcess) return res.status(400).json({ message: "Bad request" });
    newProduct.price =
      foundProcess.price + foundProcess.price * (profitMargin / 100);

    returnItem = await (await newProduct.save()).populate("productionProcess");
    await ProductionProcess.findByIdAndUpdate(
      productionProcess,
      { $push: { products: returnItem._id } },
      { new: true }
    );
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
  return res.status(201).json({ returnItem });
};

exports.updateProduct = async (req, res) => {
  try {
    if (req.body.price)
      return res
        .status(400)
        .json({ message: "You can't change price of product." });
    const updatedItem = await Product.findByIdAndUpdate(
      req.params.id,
      req.params.body
    );
    if (!updatedItem)
      return res.status(400).json({ message: "Item does not exist." });
    return res.status(201).json({ updatedItem });
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
};

exports.getSingleProduct = async (req, res) => {
  const id = req.params.id;
  let product1;
  try {
    product1 = await Product.findById(id);
  } catch (err) {
    return console.log(err);
  }
  if (!product1) {
    return res.status(404).json({ message: "No product found" });
  }
  return res.status(200).json({ product1 });
};

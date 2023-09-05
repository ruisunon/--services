const ProductionProcessItem = require("../models/ProductionProcessItem");

exports.createNewItem = async (req, res) => {
  const { materialID, quantity } = req.body;
  try {
    const newItem = new ProductionProcessItem({
      quantity,
      material: materialID,
    });
    const createdItem = await newItem.save();
    return res.status(201).json({ createdItem });
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
};
exports.getSingleItem = async (req, res) => {
  const id = req.params.id;
  let item;
  try {
    item = await ProductionProcessItem.findById(id);
    if (!item)
      return res.status(404).json({ message: "Item could not be found." });
    return res.status(200).json({ item });
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
};
exports.updateItem = async (req, res) => {
  const id = req.params.id;
  try {
    const item = await ProductionProcessItem.findByIdAndUpdate(id, req.body, {
      new: true,
    });
    if (!item)
      return res.status(404).json({ message: "Item could not be found." });
    return res.status(201).json({ item });
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
};
exports.getAllItems = async (req, res) => {
  let items;
  try {
    items = await ProductionProcessItem.find();
    return res.status(200).json({ items });
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
};

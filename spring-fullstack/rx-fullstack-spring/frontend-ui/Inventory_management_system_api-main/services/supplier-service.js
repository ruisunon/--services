const Supplier = require("../models/Supplier");

exports.getAllSuppliers = async (req, res) => {
  try {
    const suppliers = await Supplier.find({}).select(
      "name phoneNumber email uin"
    );

    return res.status(200).json(suppliers);
  } catch (error) {
    return res.status(500).json({ message: error.message });
  }
};

exports.createSupplier = async (req, res) => {
  const { name, uin, pdv, phoneNumber, contactPerson, email, materials } =
    req.body;
  const newSupplier = new Supplier({
    name,
    uin,
    pdv,
    phoneNumber,
    contactPerson,
    email,
    dateOfStart: Date.now(),
    materials,
  });
  try {
    await newSupplier.save();
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
  return res.status(201).json({ newSupplier });
};
exports.getSingleSupplier = async (req, res) => {
  const id = req.params.id;
  let supplier;
  try {
    supplier = await Supplier.findById(id).populate("materials");
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
  if (!supplier) {
    return res.status(404).json({ message: "No product found" });
  }
  return res.status(200).json({ supplier });
};
exports.updateSupplier = async (req, res) => {
  const id = req.params.id;
  let updatedSupplier;
  try {
    updatedSupplier = await Supplier.findByIdAndUpdate(id, req.body, {
      new: true,
    });
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
  if (!updatedSupplier) {
    return res.status(404).json({ message: "Supplier not found." });
  }
  return res.status(200).json({ updatedSupplier });
};
//For dropdown meni on frontend
exports.getOnlyIDS = async (req, res) => {
  try {
    const suppliers = await Supplier.find({}).select("name");
    return res.status(200).json(suppliers);
  } catch (error) {
    return res.status(500).json({ message: error.message });
  }
};

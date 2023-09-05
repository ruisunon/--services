const Material = require("../models/Material");
const Supplier = require("../models/Supplier");
exports.getAllMaterials = async (req, res) => {
  let materials;
  try {
    materials = await Material.find({}).select(
      "name quantity price isUsed minQuantity unitOfMeasure"
    );

    return res.status(200).json({ materials });
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
};

exports.createNewMaterial = async (req, res) => {
  let message = null;
  let {
    name,
    quantity,
    minQuantity,
    unitOfMeasure,
    isUsed,
    supplierId,
    price,
  } = req.body;
  if (quantity < minQuantity) {
    message =
      "Please place an order for this material since it is below minimal quantity.";
    quantity = minQuantity + 1;
  }
  const newMaterial = new Material({
    name,
    quantity,
    minQuantity,
    unitOfMeasure,
    isUsed,
    supplier: supplierId,
    price,
  });
  try {
    await newMaterial.save();
    const updatedSupplier = await Supplier.findByIdAndUpdate(
      supplierId,
      { $push: { materials: newMaterial._id } },
      { new: true }
    );
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
  return res.status(201).json({ newMaterial, message });
};

exports.getSingleMaterial = async (req, res) => {
  const id = req.params.id;
  let material;
  try {
    material = await Material.findById(id).populate("supplier");
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
  if (!material) {
    return res.status(404).json({ message: "No material found" });
  }
  return res.status(200).json({ material });
};
exports.updateMaterial = async (req, res) => {
  const id = req.params.id;
  let updatedMaterial;
  let message = null;
  try {
    const item = await Material.findById(id);
    if (req.body.minQuantity && req.body.quantity) {
      if (req.body.quantity < req.body.minQuantity) {
        //Napraviti narudzbu prema dostavljacu
        message =
          "Please place an order for this material since it is below minimal quantity.";
        req.body.quantity = req.body.minQuantity + 1;
      }
    }
    if (req.body.minQuantity) {
      if (item.quantity < req.body.minQuantity) {
        //Napraviti narudzbu prema dostavljacu
        message =
          "Please place an order for this material since it is below minimal quantity.";
        req.body.quantity = item.quantity + 1;
      }
    }
    if (req.body.quantity) {
      if (req.body.quantity < item.minQuantity) {
        //Napraviti narudzbu prema dostavljacu
        message =
          "Please place an order for this material since it is below minimal quantity.";
        req.body.quantity = item.minQuantity + 1;
      }
    }
    updatedMaterial = await Material.findByIdAndUpdate(id, req.body, {
      new: true,
    });
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
  if (!updatedMaterial) {
    return res.status(404).json({ message: "Material not found." });
  }
  return res.status(200).json({ updatedMaterial, message });
};
exports.deleteProduct = async (req, res) => {
  const id = req.params.id;

  let material;
  try {
    material = await Material.findByIdAndRemove(id);
    if (!material) {
      return res.status(404).json({ message: "Item not found." });
    }
  } catch (err) {
    return res.status(500);
  }
  return res.status(200).json({ message: "Successfully delete" });
};
exports.getMaterialsForList = async (req, res) => {
  try {
    const materials = await Material.find({}).select("name");
    return res.status(200).json(materials);
  } catch (error) {
    return res.status(500).json({ message: error.message });
  }
};

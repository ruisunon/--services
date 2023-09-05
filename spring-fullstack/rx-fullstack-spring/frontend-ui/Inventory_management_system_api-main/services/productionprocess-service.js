const ProductionProcess = require("../models/ProductionProcess");

exports.getAllProcesses = async (req, res) => {
  let processes;
  try {
    processes = await ProductionProcess.find();
    return res.status(200).json({ processes });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
};
exports.getAllForTable = async (req, res) => {
  let processes;
  try {
    processes = await ProductionProcess.find().select(
      "name price startDate endDate"
    );
    return res.status(200).json({ processes });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
};
exports.createNewProcess = async (req, res) => {
  const currentProcessActive = await ProductionProcess.findOne({
    endDate: null,
  });
  if (currentProcessActive)
    return res.status(400).json({
      message:
        "There is currently an active process, please end it before creating one.",
    });
  const { name, items } = req.body;
  const newProcess = new ProductionProcess({
    name,
    productionProcessItems: items,
    startDate: Date.now(),
  });
  try {
    const createdItem = await (
      await newProcess.save()
    ).populate({
      path: "productionProcessItems",
      populate: { path: "material" },
    });
    let price = 0;
    createdItem.productionProcessItems.map(async (item) => {
      await ProductionProcess.findByIdAndUpdate(
        item,
        { $push: { productionProcesses: createdItem._id } },
        { new: true }
      );
      return (price += item.quantity * item.material.price);
    });
    createdItem.price = price;
    await createdItem.save();
    return res.status(200).json({ createdItem });
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
};
exports.updateProcess = async (req, res) => {
  const id = req.params.id;
  let updatedItem;
  try {
    if (!req.body.items) {
      updatedItem = await ProductionProcess.findByIdAndUpdate(id, req.body);
      return res.status(200).json({ updatedItem });
    }
    const updateItem = await ProductionProcess.findById(id).populate();
    const arra = updateItem.productionProcessItems.map((el) => el.toString());
    req.body.items.forEach((e) => arra.push(e));
    updateItem.productionProcessItems = [...new Set(arra)];
    const updated = await (
      await updateItem.save()
    ).populate({
      path: "productionProcessItems",
      populate: { path: "material" },
    });
    let price = 0;
    updated.productionProcessItems.map(
      (item) => (price += item.quantity * item.material.price)
    );
    updated.price = price;
    if (req.body.name) updated.name = req.body.name;
    if (req.body.endDate) updated.endDate = req.body.endDate;
    const returnItem = await ProductionProcess.findByIdAndUpdate(id, updated, {
      new: true,
    });
    return res.status(200).json({ returnItem });
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
};

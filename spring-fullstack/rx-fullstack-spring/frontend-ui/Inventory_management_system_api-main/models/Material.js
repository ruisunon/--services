const mongoose = require("mongoose");

const materialSchema = new mongoose.Schema({
  name: {
    type: String,
    required: [true, "Name must be set."],
  },
  quantity: {
    type: Number,
    required: [true, "Quantity must be set."],
  },
  minQuantity: {
    type: Number,
    required: [true, "Minimal quantity must be set."],
  },
  price: {
    type: Number,
    required: [true, "Price must be set."],
  },
  unitOfMeasure: {
    type: String,
    required: [true, "Unit of measure must be set."],
  },
  isUsed: {
    type: Boolean,
    required: true,
    default: true,
  },
  supplier: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Supplier",
  },
});
module.exports = mongoose.model("Material", materialSchema);

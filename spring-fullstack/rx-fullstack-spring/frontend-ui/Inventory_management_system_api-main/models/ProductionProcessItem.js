const mongoose = require("mongoose");

const productionProcessItemScheme = new mongoose.Schema({
  quantity: {
    type: Number,
    required: [true, "Quantity must be set."],
  },
  productionProcesses: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "ProductionProcess",
    },
  ],
  material: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Material",
  },
});

module.exports = mongoose.model(
  "ProductionProcessItem",
  productionProcessItemScheme
);

const mongoose = require("mongoose");

const productionProcessScheme = new mongoose.Schema({
  name: {
    type: String,
    required: [true, "Name must be set."],
  },
  startDate: {
    type: Date,
    required: true,
  },
  endDate: {
    type: Date,
  },
  price: {
    type: Number,
  },
  products: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Product",
    },
  ],
  productionProcessItems: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "ProductionProcessItem",
    },
  ],
});

module.exports = mongoose.model("ProductionProcess", productionProcessScheme);

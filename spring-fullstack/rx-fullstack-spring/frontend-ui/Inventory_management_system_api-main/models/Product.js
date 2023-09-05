const mongoose = require("mongoose");

const productScheme = new mongoose.Schema({
  name: {
    type: String,
    required: [true, "Name must be set."],
  },
  picUrl: {
    type: String,
    required: [true, "Picture url must be set."],
  },
  profitMargin: {
    type: Number,
    required: [true, "Profit margin must be set."],
  },
  price: {
    type: Number,
    required: [true, "Price must be set."],
  },
  productionProcess: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "ProductionProcess",
    required: [true, "Production process must be set."],
  },
});

module.exports = mongoose.model("Product", productScheme);

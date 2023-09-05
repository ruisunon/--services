const mongoose = require("mongoose");

const supplierSchema = new mongoose.Schema({
  name: {
    type: String,
    required: [true, "Name must be set."],
  },
  uin: {
    type: String,
    required: [true, "Unique identifying number must be set."],
  },
  pdv: {
    type: Number,
    required: [true, "PDV must be set."],
  },
  phoneNumber: {
    type: String,
    required: [true, "Phone number must be set."],
  },
  contactPerson: {
    type: String,
    required: [true, "Contact person must be set."],
  },
  email: {
    type: String,
    required: [true, "Email must be set."],
  },
  dateOfStart: {
    type: Date,
    required: true,
  },
  dateOfEnd: {
    type: Date,
  },
  materials: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Material",
    },
  ],
});

module.exports = mongoose.model("Supplier", supplierSchema);

const mongoose = require("mongoose");
const { isEmail } = require("validator");
const employeeScheme = new mongoose.Schema({
  firstName: {
    type: String,
    required: [true, "First name must be set."],
  },
  lastName: {
    type: String,
    required: [true, "Last name must be set."],
  },
  phoneNumber: {
    type: String,
    required: [true, "Phone number must be set."],
  },
  email: {
    type: String,
    required: [true, "Please enter a email."],
    validate: [isEmail, "Please enter a valid email."],
  },
  dateOfJoin: {
    type: Date,
    required: true,
  },
  dateOfLeave: {
    type: Date,
  },
  user: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
  },
});

module.exports = mongoose.model("Employee", employeeScheme);

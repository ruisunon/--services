const mongoose = require("mongoose");
const bcrypt = require("bcryptjs");
const userScheme = new mongoose.Schema({
  userName: {
    type: String,
    required: [true, "Username must not be empty."],
    unique: true,
  },
  passHash: {
    type: String,
    required: [true, "Password must be set."],
  },
  role: {
    type: String,
    default: "USER",
  },
  employee: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Employee",
  },
});
userScheme.pre("save", async function (next) {
  const salt = await bcrypt.genSalt();
  this.passHash = await bcrypt.hash(this.passHash, salt);
  next();
});
userScheme.statics.login = async function (username, password) {
  const user = await this.findOne({ userName: username });
  if (user) {
    const auth = await bcrypt.compare(password, user.passHash);
    if (auth) return user;
    throw Error("Incorrect password.");
  }
  throw Error("User does not exist.");
};
module.exports = mongoose.model("User", userScheme);

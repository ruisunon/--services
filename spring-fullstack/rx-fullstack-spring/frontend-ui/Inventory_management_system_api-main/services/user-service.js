const User = require("../models/User");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");
const Employee = require("../models/Employee");
const MAX_AGE = 3 * 24 * 60 * 60;
const createToken = (id) => {
  return jwt.sign({ id }, process.env.JWT_SECRET, {
    expiresIn: MAX_AGE,
  });
};
exports.signup = async (req, res) => {
  const { userName, password, firstName, lastName, phoneNumber, email } =
    req.body;
  let existingUser;
  let savedUser;
  existingUser = await User.findOne({ userName });
  if (existingUser) {
    return res.status(400).json({ message: "User already exist!" });
  }
  const createdUser = new User({
    userName,
    passHash: password,
  });
  const newEmployee = new Employee({
    firstName,
    lastName,
    email,
    phoneNumber,
    dateOfJoin: Date.now(),
  });
  try {
    const employee = await newEmployee.save();
    createdUser.employee = employee._id;
    savedUser = await (await createdUser.save()).populate("employee");
    const token = createToken(savedUser._id);
    res.cookie("jwt", token, { httpOnly: true, maxAge: MAX_AGE * 100 }); //postavi cookie jwt, token, samo ce se moci pristupiti njemu sa backenda, traje 3 dana, brise se poslije tog
    res.cookie("logged", "logged", { maxAge: MAX_AGE * 100 });
    res.cookie("role", user.role), { maxAge: MAX_AGE * 100 };
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
  return res.status(201).json({ user: savedUser._id });
};

exports.login = async (req, res) => {
  const { userName, password } = req.body;
  try {
    const user = await User.login(userName, password);
    const token = createToken(user._id);
    res.cookie("jwt", token, { httpOnly: true, maxAge: MAX_AGE * 100 }); //postavi cookie jwt, token, samo ce se moci pristupiti njemu sa backenda
    res.cookie("logged", "logged", { maxAge: MAX_AGE * 100 });
    res.cookie("role", user.role), { maxAge: MAX_AGE * 100 };
    return res.status(200).json({ user: user._id });
  } catch (err) {
    return res.status(400).json({ message: err.message });
  }
};
exports.logout = async (req, res) => {
  res.cookie("jwt", "", { maxAge: 1 });
  return res.status(200).json({ message: "success" });
};

exports.editUser = async (req, res) => {
  const id = req.params.id;
  try {
    if (req.body.role || req.body.employee)
      return res
        .status(400)
        .json({ message: "You cannot edit role or employeeID" });
    if (req.body.passHash) {
      const salt = await bcrypt.genSalt();
      req.body.passHash = await bcrypt.hash(req.body.passHash, salt);
    }
    const user = await User.findByIdAndUpdate(id, req.body);
    if (!user)
      return res.status(404).json({ message: "User could not be found." });
    return res.status(200).json({ user });
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
};

exports.changePassword = async (req, res) => {
  const token = req.cookies.jwt;
  let id = null;
  try {
    jwt.verify(token, process.env.JWT_SECRET, async (err, decodedToken) => {
      if (err)
        return res
          .status(403)
          .json({ message: "You are not authorized for this route." });
      id = decodedToken.id;
    });
    const userToChange = await User.findById(id);
    if (!userToChange)
      return res.status(400).json({ message: "User could not be found." });
    try {
      const user = await User.login(
        userToChange.userName,
        req.body.oldPassword
      );
      if (req.body.newPassword) {
        const salt = await bcrypt.genSalt();
        req.body.passHash = await bcrypt.hash(req.body.newPassword, salt);
      }
      const updatedUser = await User.findByIdAndUpdate(id, req.body);
      return res.status(200).json({ user: user._id });
    } catch (err) {
      return res.status(400).json({ message: err.message });
    }
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
};
exports.getAllUsers = async (req, res) => {
  let users;
  try {
    users = await User.find({}).select("userName role");
    return res.status(200).json({ users });
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
};
exports.getSingleUser = async (req, res) => {
  const id = req.params.id;
  let user;
  try {
    user = await User.findById(id).populate("employee");
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
  if (!user) {
    return res.status(404).json({ message: "No user found" });
  }
  return res.status(200).json({ user });
};

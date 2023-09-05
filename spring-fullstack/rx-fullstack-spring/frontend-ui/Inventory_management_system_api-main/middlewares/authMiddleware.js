const jwt = require("jsonwebtoken");
const User = require("../models/User");
const requireAuth = async (req, res, next) => {
  const token = req.cookies.jwt;
  if (!token)
    res.status(403).json({ message: "You are not authorized for this route." });
  jwt.verify(token, process.env.JWT_SECRET, async (err, decodedToken) => {
    if (err)
      return res
        .status(401)
        .json({ message: "You are not authorized for this route." });
  });
  next();
};
const adminCheck = async (req, res, next) => {
  const token = req.cookies.jwt;
  let userID = null;
  let good = false;
  if (!token)
    res.status(401).json({ message: "You are not authorized for this route." });
  jwt.verify(token, process.env.JWT_SECRET, async (err, decodedToken) => {
    good = false;
    userID = null;
    if (err)
      return res
        .status(401)
        .json({ message: "You are not authorized for this route." });
    good = true;
    userID = decodedToken.id;
  });
  if (good) {
    const user = await User.findById(userID);
    // console.log(user.role);
    if (user?.role === "ADMIN") next();
    else
      res
        .status(403)
        .json({ message: "You are not authorized for this route." });
  }
};
module.exports = { requireAuth, adminCheck };

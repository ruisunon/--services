const express = require("express");
const router = express.Router();
const itemService = require("../services/productionprocessitem-service");
router.post("/", itemService.createNewItem);
router.get("/", itemService.getAllItems);
router.get("/:id", itemService.getSingleItem);
router.patch("/:id", itemService.updateItem);

module.exports = router;

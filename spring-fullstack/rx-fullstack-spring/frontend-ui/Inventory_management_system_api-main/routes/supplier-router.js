const express = require("express");
const router = express.Router();
const supplierService = require("../services/supplier-service");

router.get("/onlyids", supplierService.getOnlyIDS);
router.get("/", supplierService.getAllSuppliers);
router.post("/", supplierService.createSupplier);
router.patch("/:id", supplierService.updateSupplier);
router.get("/:id", supplierService.getSingleSupplier);
module.exports = router;

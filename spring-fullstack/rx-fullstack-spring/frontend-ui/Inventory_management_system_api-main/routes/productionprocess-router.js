const express = require("express");
const router = express.Router();
const productionService = require("../services/productionprocess-service");

router.get("/for-table", productionService.getAllForTable);
router.get("/", productionService.getAllProcesses);
router.post("/", productionService.createNewProcess);
router.patch("/:id", productionService.updateProcess);

module.exports = router;

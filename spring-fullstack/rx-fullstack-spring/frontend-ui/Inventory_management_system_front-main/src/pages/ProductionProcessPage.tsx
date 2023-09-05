import React from "react";
import ProductionProcessHero from "../components/production-process/ProductionProcessHero";
import CustomFooter from "../components/shared/CustomFooter";
import Header from "../components/shared/Header";

const ProductionProcessPage = () => {
  return (
    <div>
      <Header />
      <ProductionProcessHero />
      <CustomFooter />
    </div>
  );
};

export default ProductionProcessPage;

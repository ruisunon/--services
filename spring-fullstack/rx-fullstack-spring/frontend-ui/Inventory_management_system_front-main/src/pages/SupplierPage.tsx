import CustomFooter from "../components/shared/CustomFooter";
import Header from "../components/shared/Header";
import SupplierHero from "../components/suppliers/SupplierHero";

const SupplierPage = () => {
  return (
    <div className="bg-gray-600 min-h-screen">
      <Header />
      <SupplierHero />
      <CustomFooter />
    </div>
  );
};

export default SupplierPage;

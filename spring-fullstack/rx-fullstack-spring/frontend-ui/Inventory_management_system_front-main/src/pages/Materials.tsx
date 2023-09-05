import MaterialsHero from "../components/materials/MaterialsHero";
import CustomFooter from "../components/shared/CustomFooter";
import Header from "../components/shared/Header";
const Materials = () => {
  return (
    <div className="bg-gray-600 min-h-screen">
      <Header />
      <MaterialsHero />
      <CustomFooter />
    </div>
  );
};

export default Materials;

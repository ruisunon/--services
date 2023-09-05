import "./App.css";
import { Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import Materials from "./pages/Materials";
import MaterialDetails from "./pages/MaterialDetails";
import SupplierPage from "./pages/SupplierPage";
import SupplierDetails from "./pages/SupplierDetails";
import ProductionProcessPage from "./pages/ProductionProcessPage";
import ProductsPage from "./pages/ProductsPage";
import UsersPage from "./pages/UsersPage";
const App = () => {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/auth/login" />}></Route>
      <Route path="/auth/login" element={<LoginPage />} />
      <Route path="/materials" element={<Materials />} />
      <Route path="/materials/:id" element={<MaterialDetails />} />
      <Route path="/suppliers" element={<SupplierPage />} />
      <Route path="/suppliers/:id" element={<SupplierDetails />} />
      <Route path="/production-processes" element={<ProductionProcessPage />} />
      <Route path="/products" element={<ProductsPage />} />
      <Route path="/users" element={<UsersPage />} />
    </Routes>
  );
};

export default App;

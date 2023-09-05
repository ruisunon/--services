import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Material } from "../../models/interfaces";

const MaterialDetailHero = ({ id }: { id: string }) => {
  const [material, setMaterial] = useState<Material>();
  const navigate = useNavigate();
  useEffect(() => {
    fetch(`http://localhost:3000/materials/${id}`, {
      credentials: "include",
    })
      .then((res: any) => {
        if (res.status === 400) {
          throw new Error("Please check your data validity.");
        } else if (res.status === 401 || res.status === 403) {
          navigate("/auth/login");
        }
        return res.json();
      })
      .then((data) => {
        setMaterial(data.material);
        console.log(data.material);
      });
  }, []);
  const returnRow = (name: string, value: string | number | boolean) => {
    return (
      <div className="mt-6 flex justify-center items-center gap-3 text-white">
        <h1 className="text-center text-xl">{name}:</h1>
        <h1 className="text-center text-xl text-gray-300">
          {typeof value == "boolean" ? (value ? "true" : "false") : value}
        </h1>
      </div>
    );
  };

  return (
    <section className="flex flex-col bg-gray-700 h-[800px] md:h-screen md:flex-row md:justify-center align-center md:gap-20">
      <div className="mt-10 md:flex md:flex-col md:items-start">
        <h1 className="text-3xl text-blue-200 text-center">Material</h1>
        {returnRow("Name", material?.name || "")}
        {returnRow("Quantity", material?.quantity || "")}
        {returnRow("Min Quantity", material?.minQuantity || "")}
        {returnRow("Is Used", material?.isUsed || "")}
        {returnRow("Price", material?.price || "")}
        {returnRow("Unit of Measure", material?.unitOfMeasure || "")}
      </div>
      <div className="mt-10 md:flex md:flex-col md:items-end">
        <h1 className="text-3xl text-blue-200 text-center">Supplier</h1>
        {returnRow("ID", material?.supplier?._id || "")}
        {returnRow("Name", material?.supplier?.name || "")}
        {returnRow("Contact person", material?.supplier?.contactPerson || "")}
        {returnRow("PDV", material?.supplier?.pdv + " %" || "")}
        {returnRow("Phone Number", material?.supplier?.phoneNumber || "")}
        {returnRow("UIN", material?.supplier?.uin || "")}
      </div>
    </section>
  );
};
export default MaterialDetailHero;

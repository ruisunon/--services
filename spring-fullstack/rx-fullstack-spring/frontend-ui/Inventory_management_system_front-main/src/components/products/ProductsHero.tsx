import { Button, Modal } from "flowbite-react";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Product } from "../../models/interfaces";
import Table from "../shared/Table";

const ProductsHero = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const navigate = useNavigate();
  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_URL}/products`, {
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
      .then((data) => setProducts(data.products));
  }, []);
  console.log(products);
  return (
    <div>
      {" "}
      <Table head={["ID", "Name", "ProfitMargin", "Price"]}>
        {products.map((product, index) => {
          return (
            <tr
              key={product._id}
              className=" border-b  border-gray-700 hover:bg-gray-600 bg-gray-700"
            >
              <th
                scope="row"
                className="px-6 py-4 font-medium whitespace-nowrap text-white"
              >
                {product._id}
              </th>
              <td className="px-6 py-4">{product.name}</td>
              <td className="px-6 py-4">{product.profitMargin}</td>
              <td className="px-6 py-4">{product.price}</td>

              <td className="flex items-center px-6 py-4 space-x-3">
                <Link
                  to={`/materials/${product._id}`}
                  className="font-medium text-blue-600 dark:text-blue-500 hover:underline"
                >
                  Details
                </Link>
                {/* EDIT MODAL */}
                <Button
                  onClick={() => {
                    // onClick();
                    // setModalItem(materials[index]);
                    // setModalIndex(index);
                  }}
                >
                  Edit
                </Button>
              </td>
            </tr>
          );
        })}
      </Table>
    </div>
  );
};

export default ProductsHero;

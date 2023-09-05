import { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  Button,
  Checkbox,
  Label,
  TextInput,
  Modal,
  Spinner,
} from "flowbite-react";
import AddMaterial from "./AddMaterial";
import Table from "../shared/Table";
interface Material {
  _id: string;
  name: string;
  quantity: number;
  price: number;
  isUsed: boolean;
  unitOfMeasure: string;
  minQuantity: number;
}
const MaterialsHero = () => {
  const [openAddModal, setOpenAddModal] = useState(false);
  const [deleteID, setDeleteID] = useState<string>();
  const [materials, setMaterials] = useState<Material[]>([]);
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [submitEdit, setSubmitEdit] = useState<boolean>(false);
  const [modalItem, setModalItem] = useState<Material>();
  const [modalIndex, setModalIndex] = useState(0);
  const [openDeleteModal, setOpenDeleteModal] = useState<boolean>(false);
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const formRef = useRef<HTMLFormElement>(null);
  const materialName = useRef<HTMLInputElement>(null);
  const materialQuantity = useRef<HTMLInputElement>(null);
  const materialMinQuantity = useRef<HTMLInputElement>(null);
  const materialIsUsed = useRef<HTMLInputElement>(null);
  const materialPrice = useRef<HTMLInputElement>(null);
  const materialUnitOfMeasurement = useRef<HTMLInputElement>(null);
  const [suppliers, setSuppliers] = useState<{ _id: string; name: string }[]>(
    []
  );
  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_URL}/materials`, {
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
      .then((data) => setMaterials(data.materials));

    fetch(`${import.meta.env.VITE_API_URL}/suppliers/onlyids`, {
      credentials: "include",
    })
      .then((res) => res.json())
      .then((data) => setSuppliers(data));
  }, []);
  useEffect(() => {});
  const onClick = () => {
    setModalOpen(true);
  };
  const onClose = () => {
    setModalOpen(false);
  };
  const editHandler = (e: any) => {
    e.preventDefault();
    const updateObject: { [key: string]: any } = {};
    if (materialName.current?.value !== modalItem?.name)
      updateObject.name = materialName.current?.value;
    if (materialQuantity.current?.value != modalItem?.quantity.toString())
      updateObject.quantity = materialQuantity.current?.value
        ? +materialQuantity.current.value
        : 1;
    if (
      materialMinQuantity.current?.value !== modalItem?.minQuantity.toString()
    )
      updateObject.minQuantity = materialMinQuantity.current?.value
        ? +materialMinQuantity.current.value
        : 1;
    if (materialPrice.current?.value != modalItem?.price.toString())
      updateObject.price = materialPrice.current?.value
        ? +materialPrice.current.value
        : 1;
    if (materialIsUsed.current?.value !== modalItem?.isUsed.toString())
      updateObject.isUsed =
        materialIsUsed.current?.value === "true" ? true : false;
    if (materialUnitOfMeasurement.current?.value !== modalItem?.unitOfMeasure)
      updateObject.unitOfMeasure = materialUnitOfMeasurement.current?.value;
    console.log("sent object");
    console.log(updateObject);
    fetch(`${import.meta.env.VITE_API_URL}/materials/${modalItem?._id}`, {
      method: "PATCH",
      body: JSON.stringify(updateObject),
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res: any) => {
        if (res.status === 401 || res.status === 403) {
          navigate("/auth/login");
        } else if (res.status === 400) {
          setError("Data not valid");
          return;
        }
        return res.json();
      })
      .then((data) => {
        console.log(data);
        materials[modalIndex] = data.updatedMaterial;
        setSubmitEdit(false);
        formRef.current?.reset();
        onClose();
        setError("");
      });
  };
  const deleteItem = () => {
    fetch(`${import.meta.env.VITE_API_URL}/materials/${deleteID}`, {
      method: "DELETE",
      credentials: "include",
    })
      .then((res) => res.json())
      .then((data) => {
        setMaterials(materials.filter((material) => material._id !== deleteID));
        setOpenDeleteModal(false);
      });
  };
  console.log(materials);
  return (
    <div className="lg:flex md:w-full min-h-screen">
      <div className="w-full h-full bg-gray-700 lg:h-[400px] lg:w-72">
        <div className="flex justify-center items-center pt-6 text-2xl text-white lg:text-lg h-1/2">
          <h1 className="">Number of materials:</h1>
          <h1 className="">{materials.length}</h1>
        </div>
        <div className="flex justify-center items-center pt-6 text-2xl text-white lg:text-lg h-1/2">
          <h1 className="">Used elements:</h1>
          <h1 className="">
            {materials.filter((el) => el.isUsed == true).length}
          </h1>
        </div>
      </div>
      <div className="relative overflow-x-auto shadow-md md:w-full border-t border-gray-200 lg:border-none">
        <Table
          head={[
            "ID",
            "Name",
            "Quantity/MinQuantity",
            "Price",
            "isUsed",
            "Unit of Measure",
          ]}
        >
          {materials.map((material, index) => {
            return (
              <tr
                key={material._id}
                className=" border-b  border-gray-700 hover:bg-gray-600 bg-gray-700"
              >
                <th
                  scope="row"
                  className="px-6 py-4 font-medium whitespace-nowrap text-white"
                >
                  {material._id}
                </th>
                <td className="px-6 py-4">{material.name}</td>
                <td className="px-6 py-4">
                  {material.quantity}/{material.minQuantity}
                </td>
                <td className="px-6 py-4">{material.price}</td>
                <td className="px-6 py-4">
                  {material.isUsed == true ? "Yes" : "No"}
                </td>
                <td className="px-6 py-4">{material.unitOfMeasure}</td>
                <td className="flex items-center px-6 py-4 space-x-3">
                  <Link
                    to={`/materials/${material._id}`}
                    className="font-medium text-blue-600 dark:text-blue-500 hover:underline"
                  >
                    Details
                  </Link>
                  {/* EDIT MODAL */}
                  <Button
                    onClick={() => {
                      onClick();
                      setModalItem(materials[index]);
                      setModalIndex(index);
                    }}
                  >
                    Edit
                  </Button>
                  <Modal
                    show={modalOpen}
                    position="center"
                    onClose={() => {
                      onClose();
                      formRef.current?.reset();
                      setSubmitEdit(false);
                    }}
                  >
                    <Modal.Header>Edit material</Modal.Header>
                    <Modal.Body>
                      <form onSubmit={editHandler} ref={formRef}>
                        <div className="flex flex-col md:flex-row md:justify-evenly">
                          <div>
                            <div>
                              <div className="mb-2 block">
                                <Label
                                  htmlFor="name"
                                  value="Name of material"
                                />
                              </div>
                              <TextInput
                                id="name"
                                type="text"
                                defaultValue={modalItem?.name}
                                ref={materialName}
                              />
                            </div>
                            <div>
                              <div className="mb-2 block">
                                <Label htmlFor="quantity" value="Quantity" />
                              </div>
                              <TextInput
                                id="quantity"
                                type="text"
                                defaultValue={modalItem?.quantity}
                                ref={materialQuantity}
                              />
                            </div>
                            <div>
                              <div className="mb-2 block">
                                <Label htmlFor="isused" value="Is Used" />
                              </div>
                              <TextInput
                                id="minquantity"
                                type="text"
                                defaultValue={
                                  modalItem?.isUsed ? "true" : "false"
                                }
                                ref={materialIsUsed}
                              />
                            </div>
                          </div>
                          <div>
                            <div>
                              <div className="mb-2 block">
                                <Label htmlFor="price" value="Price" />
                              </div>
                              <TextInput
                                id="price"
                                type="text"
                                defaultValue={modalItem?.price}
                                ref={materialPrice}
                              />
                            </div>
                            <div>
                              <div className="mb-2 block">
                                <Label
                                  htmlFor="unitofMeasure"
                                  value="Unit of Measure"
                                />
                              </div>
                              <TextInput
                                id="unitofmeasure"
                                type="text"
                                defaultValue={modalItem?.unitOfMeasure}
                                ref={materialUnitOfMeasurement}
                              />
                            </div>
                            <div>
                              <div className="mb-2 block">
                                <Label
                                  htmlFor="minquantity"
                                  value="Min Quantity"
                                />
                              </div>
                              <TextInput
                                id="minquantity"
                                type="text"
                                defaultValue={modalItem?.minQuantity}
                                ref={materialMinQuantity}
                              />
                            </div>
                          </div>
                        </div>
                        <Modal.Footer className="mt-4 mb-0">
                          <Button
                            type="submit"
                            onClick={() => {
                              setSubmitEdit(true);
                            }}
                          >
                            {submitEdit ? (
                              <span>
                                <Spinner aria-label="Spinner button example" />
                                <span className="pl-3">Loading...</span>
                              </span>
                            ) : (
                              <span>Submit</span>
                            )}
                            <span className="text-red-500">{error}</span>
                          </Button>
                        </Modal.Footer>
                      </form>
                    </Modal.Body>
                  </Modal>
                  <a
                    href="#"
                    className="font-medium text-red-600 dark:text-red-500 hover:underline"
                    onClick={() => {
                      setOpenDeleteModal(true);
                      console.log(material._id);
                      setDeleteID(material._id);
                    }}
                  >
                    Remove
                  </a>
                </td>
                <Modal
                  show={openDeleteModal}
                  size="md"
                  popup={true}
                  onClose={() => {
                    setOpenDeleteModal(false);
                  }}
                >
                  <Modal.Header />
                  <Modal.Body>
                    <div className="text-center">
                      <h3 className="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">
                        Are you sure you want to delete this product?
                      </h3>
                      <div className="flex justify-center gap-4">
                        <Button
                          color="failure"
                          onClick={() => {
                            deleteItem();
                          }}
                        >
                          Yes, I'm sure
                        </Button>
                        <Button
                          color="gray"
                          onClick={() => {
                            setOpenDeleteModal(false);
                          }}
                        >
                          No, cancel
                        </Button>
                      </div>
                    </div>
                  </Modal.Body>
                </Modal>
              </tr>
            );
          })}
        </Table>
      </div>
      <Button
        color="purple"
        pill={true}
        onClick={() => setOpenAddModal(true)}
        className="fixed bottom-0 right-0 mr-10 mb-10"
      >
        Add new material
      </Button>
      <AddMaterial
        modalOpen={openAddModal}
        onClose={() => {
          setOpenAddModal(false);
        }}
        onOpen={() => {
          setOpenAddModal(true);
        }}
        setMaterials={setMaterials}
        suppliers={suppliers}
      />
    </div>
  );
};

export default MaterialsHero;

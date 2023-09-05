import {
  Modal,
  Label,
  TextInput,
  Button,
  Spinner,
  Alert,
} from "flowbite-react";
import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
const AddMaterial = ({
  modalOpen,
  onOpen,
  onClose,
  setMaterials,
  suppliers,
}: {
  modalOpen: boolean;
  onOpen: () => void;
  onClose: () => void;
  setMaterials: any;
  suppliers: { _id: string; name: string }[];
}) => {
  // const [modalOpen, setModalOpen] = useState(false);
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [hasError, setHasError] = useState(false);
  const formRef = useRef<HTMLFormElement>(null);
  const materialName = useRef<HTMLInputElement>(null);
  const materialQuantity = useRef<HTMLInputElement>(null);
  const materialMinQuantity = useRef<HTMLInputElement>(null);
  const materialIsUsed = useRef<HTMLSelectElement>(null);
  const materialPrice = useRef<HTMLInputElement>(null);
  const materialUnitOfMeasure = useRef<HTMLInputElement>(null);
  const materialSupplierId = useRef<HTMLSelectElement>(null);
  const submitHandler = (e: any) => {
    e.preventDefault();
    const addObj = {
      name: materialName.current?.value,
      quantity:
        materialQuantity.current?.value && +materialQuantity.current.value,
      minQuantity:
        materialMinQuantity.current?.value &&
        +materialMinQuantity.current.value,
      unitOfMeasure: materialUnitOfMeasure.current?.value,
      isUsed: Boolean(materialIsUsed.current?.value),
      supplierId: materialSupplierId.current?.value,
      price: materialPrice.current?.value && +materialPrice.current.value,
    };
    console.log(addObj);
    console.log(Boolean(materialIsUsed.current?.value));
    fetch("http://localhost:3000/materials", {
      method: "POST",
      body: JSON.stringify(addObj),
      headers: {
        "Content-Type": "application/json",
      },
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
        setHasError(false);
        setError("");
        onClose();
        console.log(data);
        setMaterials((prev: any) => [...prev, data.newMaterial]);
        formRef.current?.reset();
      })
      .catch((err) => {
        setHasError(true);
        setError(err.message);
        formRef.current?.reset();
        setTimeout(() => {
          setHasError(false);
        }, 3000);
      });
  };
  return (
    <Modal
      show={modalOpen}
      position="center"
      onClose={() => {
        onClose();
        formRef.current?.reset();
      }}
    >
      <Modal.Header>Edit material</Modal.Header>
      <Modal.Body>
        {hasError && (
          <Alert color="failure">
            <span>
              <span className="font-medium">Info alert!</span> {error}
            </span>
          </Alert>
        )}
        <form onSubmit={submitHandler} ref={formRef}>
          <div className="flex flex-col md:flex-row md:justify-evenly">
            <div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="name" value="Name of material" />
                </div>
                <TextInput id="name" type="text" ref={materialName} />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="quantity" value="Quantity" />
                </div>
                <TextInput id="quantity" type="text" ref={materialQuantity} />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="isused" value="Is Used" />
                </div>
                <select id="cars" name="cars" ref={materialIsUsed}>
                  <option value="true">true</option>
                  <option value="false">false</option>
                </select>
                {/* <TextInput id="minquantity" type="text" ref={materialIsUsed} /> */}
              </div>
            </div>
            <div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="price" value="Price" />
                </div>
                <TextInput id="price" type="text" ref={materialPrice} />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="unitofMeasure" value="Unit of Measure" />
                </div>
                <TextInput
                  id="unitofmeasure"
                  type="text"
                  ref={materialUnitOfMeasure}
                />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="minquantity" value="Min Quantity" />
                </div>
                <TextInput
                  id="minquantity"
                  type="text"
                  ref={materialMinQuantity}
                />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="supplierid" value="Supplier" />
                </div>
                <select id="cars" name="cars" ref={materialSupplierId}>
                  {suppliers.map((supplier) => (
                    <option value={supplier._id}>{supplier.name}</option>
                  ))}
                </select>
                {/* <TextInput
                  id="supplierid"
                  type="text"
                  ref={materialSupplierId}
                /> */}
              </div>
            </div>
          </div>
          <Modal.Footer className="mt-4 mb-0">
            <Button type="submit">
              <span>Submit</span>
            </Button>
          </Modal.Footer>
        </form>
      </Modal.Body>
    </Modal>
  );
};

export default AddMaterial;

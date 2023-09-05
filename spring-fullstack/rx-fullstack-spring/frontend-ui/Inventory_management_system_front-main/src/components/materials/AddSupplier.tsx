import { Button, Checkbox, Label, Modal, TextInput } from "flowbite-react";
import React, { Dispatch, SetStateAction, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { Supplier } from "../../models/interfaces";

interface SupplierProps {
  openModal: boolean;
  onClose: () => void;
  setSuppliers: Dispatch<SetStateAction<Supplier[]>>;
}

const AddSupplier = ({ openModal, onClose, setSuppliers }: SupplierProps) => {
  const navigate = useNavigate();
  const NameRef = useRef<HTMLInputElement>(null);
  const UINRef = useRef<HTMLInputElement>(null);
  const pdvRef = useRef<HTMLInputElement>(null);
  const phoneNumberRef = useRef<HTMLInputElement>(null);
  const contactPersonRef = useRef<HTMLInputElement>(null);
  const emailRef = useRef<HTMLInputElement>(null);
  const submitHandler = (e: any) => {
    e.preventDefault();
    const sendDataObj = {
      name: NameRef.current?.value,
      uin: UINRef.current?.value,
      pdv: pdvRef.current?.value,
      phoneNumber: phoneNumberRef.current?.value,
      contactPerson: contactPersonRef.current?.value,
      email: emailRef.current?.value,
    };
    fetch(`${import.meta.env.VITE_API_URL}/suppliers`, {
      method: "POST",
      credentials: "include",
      body: JSON.stringify(sendDataObj),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (res.status == 401 || 403) navigate("/suppliers");
        return res.json();
      })
      .then((data) => {
        console.log(data);
        setSuppliers((prevSuppliers) => [...prevSuppliers, data.newSupplier]);
        onClose();
      });
  };
  return (
    <Modal show={openModal} onClose={onClose}>
      <Modal.Header>Terms of Service</Modal.Header>
      <form className="mx-auto" onSubmit={submitHandler}>
        <Modal.Body className="flex">
          <div className="flex flex-col lg:flex-row gap-10 mx-auto">
            <div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="name" value="Supplier name" />
                </div>
                <TextInput
                  id="name"
                  placeholder="Supplier name"
                  required={true}
                  ref={NameRef}
                />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="uin" value="UIN" />
                </div>
                <TextInput
                  id="UIN"
                  type="text"
                  required={true}
                  placeholder={"UIN"}
                  ref={UINRef}
                />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="pdv" value="PDV" />
                </div>
                <TextInput
                  id="pdv"
                  type="text"
                  required={true}
                  placeholder="PDV"
                  ref={pdvRef}
                />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="phone number" value="Phone number" />
                </div>
                <TextInput
                  id="phone number"
                  type="text"
                  required={true}
                  placeholder="Phone Number"
                  ref={phoneNumberRef}
                />
              </div>
            </div>
            <div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="contact person" value="Contact person" />
                </div>
                <TextInput
                  id="contact person"
                  placeholder="Contact person"
                  required={true}
                  ref={contactPersonRef}
                />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="email" value="Email" />
                </div>
                <TextInput
                  id="email"
                  type="text"
                  required={true}
                  placeholder="Email"
                  ref={emailRef}
                />
              </div>
            </div>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button type="submit">Submit</Button>
        </Modal.Footer>
      </form>
    </Modal>
  );
};

export default AddSupplier;
{
  /* <Modal show={openModal} size="md" popup={true} onClose={onClose}>
      <Modal.Header />
      <Modal.Body>
        <div className="space-y-6 px-6 pb-4 sm:pb-6 lg:px-8 xl:pb-8 w-[800px]">
          <h3 className="text-xl font-medium text-gray-900 dark:text-white">
            Add new supplier
          </h3>
          <div className="flex flex-col lg:flex-row ">
            <div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="name" value="Your name" />
                </div>
                <TextInput id="name" placeholder="Your name" required={true} />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="uin" value="UIN" />
                </div>
                <TextInput id="UIN" type="text" required={true} />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="pdv" value="PDV" />
                </div>
                <TextInput id="pdv" type="text" required={true} />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="phone number" value="Phone number" />
                </div>
                <TextInput id="phone number" type="text" required={true} />
              </div>
            </div>
            <div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="name" value="Your name" />
                </div>
                <TextInput id="name" placeholder="Your name" required={true} />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="uin" value="UIN" />
                </div>
                <TextInput id="UIN" type="text" required={true} />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="pdv" value="PDV" />
                </div>
                <TextInput id="pdv" type="text" required={true} />
              </div>
              <div>
                <div className="mb-2 block">
                  <Label htmlFor="phone number" value="Phone number" />
                </div>
                <TextInput id="phone number" type="text" required={true} />
              </div>
            </div>
          </div>
          <div className="flex justify-between">
            <a
              href="/modal"
              className="text-sm text-blue-700 hover:underline dark:text-blue-500"
            >
              Lost Password?
            </a>
          </div>
          <div className="w-full">
            <Button>Log in to your account</Button>
          </div>
          <div className="text-sm font-medium text-gray-500 dark:text-gray-300">
            Not registered?{" "}
            <a
              href="/modal"
              className="text-blue-700 hover:underline dark:text-blue-500"
            >
              Create account
            </a>
          </div>
        </div>
      </Modal.Body>
    </Modal> */
}

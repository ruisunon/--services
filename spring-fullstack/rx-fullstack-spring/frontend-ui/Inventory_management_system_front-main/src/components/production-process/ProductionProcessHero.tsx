import { Button, Label, Modal, TextInput } from "flowbite-react";
import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Process } from "../../models/interfaces";
import Table from "../shared/Table";

const ProductionProcessHero = () => {
  const navigate = useNavigate();
  const [processes, setProcesses] = useState<Process[]>([]);
  const [error, setError] = useState();
  const [modalOpen, setModalOpen] = useState(false);
  const formRef = useRef<HTMLFormElement>(null);
  const processName = useRef<HTMLInputElement>(null);
  const editHandler = (e: any) => {
    e.preventDefault();
  };
  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_URL}/process`, {
      credentials: "include",
    })
      .then((res) => {
        console.log(res);
        if (res.status == 401 || res.status == 403) navigate("/auth/login");
        return res.json();
      })
      .then((data: any) => setProcesses(data.processes));
  }, []);
  const onClose = () => {
    setModalOpen(false);
  };
  const onOpen = () => {
    setModalOpen(true);
  };
  return (
    <div className="lg:flex md:w-full min-h-screen bg-[#4b5563]">
      <div className="w-full h-full bg-gray-700 lg:h-[400px] lg:w-72">
        <div className="flex justify-center items-center pt-6 text-2xl text-white lg:text-lg h-1/2">
          <h1 className="">Number of processes:</h1>
          <h1 className="">{processes.length}</h1>
        </div>
        <div className="flex justify-center items-center pt-6 text-2xl text-white lg:text-lg h-1/2">
          <h1 className="">Ended processes:</h1>
          <h1 className="">
            {processes.filter((el) => el.endDate != null).length}
          </h1>
        </div>
      </div>
      <div className="relative overflow-x-auto shadow-md md:w-full border-t border-gray-200 lg:border-none">
        <Table head={["ID", "Name", "Start Date", "End Date", "Price"]}>
          {processes.map((process, index) => {
            return (
              <tr
                key={process._id}
                className=" border-b  border-gray-700 hover:bg-gray-600 bg-gray-700"
              >
                <th
                  scope="row"
                  className="px-6 py-4 font-medium whitespace-nowrap text-white"
                >
                  {process._id}
                </th>
                <td className="px-6 py-4">{process.name}</td>
                <td className="px-6 py-4">{process.startDate}</td>
                <td className="px-6 py-4">{process.endDate}</td>
                <td className="px-6 py-4">{process.price}</td>
                <td className="flex items-center px-6 py-4 space-x-3">
                  {/* EDIT MODAL */}
                  <Button
                    onClick={() => {
                      onOpen();
                      // onClick();
                      // setModalItem(materials[index]);
                      // setModalIndex(index);
                    }}
                  >
                    Edit
                  </Button>
                  <Modal
                    show={modalOpen}
                    position="center"
                    onClose={() => {
                      onClose();
                      // formRef.current?.reset();
                      // setSubmitEdit(false);
                    }}
                  >
                    <Modal.Header>Edit material</Modal.Header>
                    <Modal.Body>
                      <form onSubmit={editHandler} ref={formRef}>
                        <div className="flex flex-col md:flex-row md:justify-evenly">
                          <div>
                            <div>
                              <div className="mb-2 block">
                                <Label htmlFor="name" value="Name of process" />
                              </div>
                              <TextInput
                                id="name"
                                type="text"
                                defaultValue={process?.name}
                                ref={processName}
                              />
                            </div>
                          </div>
                        </div>
                        <Modal.Footer className="mt-4 mb-0">
                          <Button
                            type="submit"
                            onClick={() => {
                              // setSubmitEdit(true);
                            }}
                          >
                            Submit
                          </Button>
                        </Modal.Footer>
                      </form>
                    </Modal.Body>
                  </Modal>
                </td>
              </tr>
            );
          })}
        </Table>
      </div>
      <Button
        color="purple"
        pill={true}
        // onClick={() => setOpenAddModal(true)}
        className="fixed bottom-0 right-0 mr-10 mb-10"
      >
        Add new process
      </Button>
    </div>
  );
};

export default ProductionProcessHero;

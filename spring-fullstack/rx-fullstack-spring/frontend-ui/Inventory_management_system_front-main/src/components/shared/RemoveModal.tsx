import { Button, Modal } from "flowbite-react";
import React from "react";

interface RemoveProps {
  openDeleteModal: boolean;
  setOpenDeleteModal: () => void;
  deleteItem: () => void;
}
const RemoveModal = ({
  openDeleteModal,
  setOpenDeleteModal,
  deleteItem,
}: RemoveProps) => {
  return (
    <Modal
      show={openDeleteModal}
      size="md"
      popup={true}
      onClose={() => {
        setOpenDeleteModal();
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
                setOpenDeleteModal();
              }}
            >
              No, cancel
            </Button>
          </div>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default RemoveModal;

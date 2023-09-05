import { Alert, Button, Label, Modal, TextInput } from "flowbite-react";
import React, { Children, useRef } from "react";

interface EditProps {
  submitHandler: (e: any) => void;
  modalOpen: boolean;
  onClose: () => void;
  children: React.ReactNode;
  error: string;
}
const EditModal = ({
  submitHandler,
  modalOpen,
  onClose,
  children,
  error,
}: EditProps) => {
  const formRef = useRef<HTMLFormElement>(null);
  return (
    <Modal
      show={modalOpen}
      position="center"
      onClose={() => {
        onClose();
        formRef.current?.reset();
        // setSubmitEdit(false);
      }}
    >
      <Modal.Header>Edit Supplier</Modal.Header>
      <Modal.Body>
        {error?.trim().length > 0 && (
          <Alert color="failure">
            <span>
              <span className="font-medium">Info alert!</span> {error}
            </span>
          </Alert>
        )}
        <form onSubmit={submitHandler} ref={formRef}>
          <div className="flex flex-col md:flex-row md:justify-evenly">
            {children}
          </div>
          <Modal.Footer className="mt-4 mb-0">
            <Button
              type="submit"
              onClick={() => {
                // setSubmitEdit(true);
              }}
            >
              <span>Submit</span>
            </Button>
          </Modal.Footer>
        </form>
      </Modal.Body>
    </Modal>
  );
};

export default EditModal;

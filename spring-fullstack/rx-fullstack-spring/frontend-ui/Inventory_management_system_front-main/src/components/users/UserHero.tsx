import { Button } from "flowbite-react";
import Table from "../shared/Table";
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { User } from "../../models/interfaces";

const UserHero = () => {
  const [users, setUsers] = useState<User[]>([]);
  const navigate = useNavigate();
  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_URL}/auth`, {
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
      .then((data) => setUsers(data.users));
  }, []);
  return (
    <div className="min-h-screen bg-gray-700">
      {" "}
      <Table head={["ID", "Name", "Role"]}>
        {users.map((user, index) => {
          return (
            <tr
              key={user._id}
              className=" border-b  border-gray-700 hover:bg-gray-600 bg-gray-700"
            >
              <th
                scope="row"
                className="px-6 py-4 font-medium whitespace-nowrap text-white"
              >
                {user._id}
              </th>
              <td className="px-6 py-4">{user.userName}</td>
              <td className="px-6 py-4">{user.role}</td>
            </tr>
          );
        })}
      </Table>
    </div>
  );
};

export default UserHero;

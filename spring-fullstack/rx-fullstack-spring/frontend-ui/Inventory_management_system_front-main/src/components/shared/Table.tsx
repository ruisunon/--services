import { ReactElement } from "react";

interface TableProps {
  head: string[];
  children: React.ReactNode;
}
// ID NAME Phone Number UIN EMAIL
const Table = ({ head, children }: TableProps) => {
  return (
    <table className="w-full text-sm text-left  text-gray-400 bg-gray-700">
      <thead className="text-xs  uppercase bg-gray-700 text-gray-400">
        <tr>
          {head.map((headtitle) => (
            <th scope="col" className="px-6 py-3" key={headtitle}>
              {headtitle}
            </th>
          ))}
          <th scope="col" className="px-6 py-3"></th>
        </tr>
      </thead>
      <tbody className="bg-gray-700">{children}</tbody>
    </table>
  );
};

export default Table;

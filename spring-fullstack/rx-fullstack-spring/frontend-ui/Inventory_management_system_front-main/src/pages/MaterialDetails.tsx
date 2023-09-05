import { useParams } from "react-router-dom";
import MaterialDetailHero from "../components/materialdetail/MaterialDetailHero";
import Header from "../components/shared/Header";

const MaterialDetails=()=>{
    const param =useParams();
    console.log(param.id);
    return <div>
        <Header/>
        <MaterialDetailHero id={param.id || ""}/>
    </div>
}
export default MaterialDetails;
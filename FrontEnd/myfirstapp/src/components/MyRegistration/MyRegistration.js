import React, { useContext } from "react";
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import HistoryOutlinedIcon from '@material-ui/icons/HistoryOutlined';
import AddOutlinedIcon from '@material-ui/icons/AddOutlined';
import { UserContext } from "../../App";
import { GetUserInfo } from "../../axios/UserAPI";
import { DeleteRegistration } from "../../axios/RegistrationAPI";
import "./MyRegistration.css";
import { withStyles } from '@material-ui/core/styles';
import HighlightOffIcon from '@material-ui/icons/HighlightOff';
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';


const MyRegistration = (props) => {

    const currentUser = useContext(UserContext);
    
    //HOC
    const StyledTableCell = withStyles(() => ({
        head: {
            backgroundColor: '#0066ff',
            color: 'white',
            fontSize: '2vw',
            fontWeight:'bolder'
        },
        body: {
            fontSize: '1vw',
        },
    }))(TableCell);

    //HOC
    const StyledTableRow = withStyles((theme) => ({
        root: {
          '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.action.hover,
          },
        },
      }))(TableRow);

    const { user } = GetUserInfo(currentUser.userState.user && currentUser.userState.user.id);

    const {onSellCopyList} = user ? user.shops[0] : '';

    const removeRegistration = (regId) => {
        DeleteRegistration(regId);
        window.location.reload();
    }
    
    return (
        <div className="myreg--page">
            <div className="myreg--header">
                <div className="myreg--header-name">My Registration</div>
                <div className="myreg--btns">
                    <Button
                        className="myreg--btn"
                        startIcon={<HistoryOutlinedIcon/>}
                        style={{ 
                            border: '2px solid #06f', 
                            borderRadius:'1vh',
                            height: '5vh',
                            marginRight: '2vw',
                            color: '#06f',
                            fontWeight: 'bolder',
                            outline: 'none'
                        }} 
                    >Sale History</Button>
                    <Button
                        className="myreg--btn"
                        startIcon={<AddOutlinedIcon/>}
                        style={{ 
                            border: '2px solid #06f', 
                            borderRadius:'1vh',
                            height: '5vh',
                            marginRight: '2vw',
                            color: '#06f',
                            fontWeight: 'bolder',
                            outline: 'none'
                        }} 
                    >New Registration</Button>
                </div>
            </div>
            <div className="myreg--table">
                <TableContainer component={Paper}>
                    <Table aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <StyledTableCell>ID</StyledTableCell>
                                <StyledTableCell>Cover</StyledTableCell>
                                <StyledTableCell>Name</StyledTableCell>
                                <StyledTableCell>Price</StyledTableCell>
                                <StyledTableCell>Status</StyledTableCell>
                                <StyledTableCell>Actions</StyledTableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody> 
                            {onSellCopyList && onSellCopyList.map(row => (
                                <StyledTableRow key={row.registrationId}>
                                    <StyledTableCell component="th" scope="row">
                                        {row.registrationId}
                                    </StyledTableCell>
                                    <StyledTableCell><img className="myreg--cover" src="/pics/book-2.jpg" alt="book"/></StyledTableCell>
                                    <StyledTableCell>{row.bookTitle}</StyledTableCell>
                                    <StyledTableCell>${row.price}</StyledTableCell>
                                    <StyledTableCell>{row.status === "approved" ? <span className="myreg--approved-text">Approved</span> : <span className="myreg--pending-text">Awaiting Approval</span>}</StyledTableCell>
                                    <StyledTableCell>
                                        <Button 
                                            variant="contained"
                                            endIcon={<MoreHorizIcon/>}
                                            style={{ 
                                                backgroundColor: '#0066ff',
                                                borderRadius:'2vh',
                                                height: '5vh',
                                                marginRight: '1.5vw',
                                                color: 'white',
                                                fontWeight: 'bolder',
                                                outline: 'none'
                                            }}
                                        >Details</Button>
                                        <Button 
                                            variant="contained" 
                                            endIcon={<HighlightOffIcon/>}
                                            style={{ 
                                                backgroundColor: '#fd0707',
                                                borderRadius:'2vh',
                                                height: '5vh',
                                                color: 'white',
                                                fontWeight: 'bolder',
                                                outline: 'none'
                                            }}
                                            onClick={() => removeRegistration(row.registrationId)}
                                        >Delete</Button>
                                    </StyledTableCell>
                                </StyledTableRow>))
                            }
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </div>
    );
}

export default MyRegistration;
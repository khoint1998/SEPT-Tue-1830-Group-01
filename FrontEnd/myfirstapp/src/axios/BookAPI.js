import { userAxios } from "./axiosClient";
import useSWR from 'swr';


export const CreateABook = async (bookDetails) => {

    const req = await userAxios('book').post('books/create', bookDetails,
        {
            headers: {
                'Authorization': `${localStorage.jwtToken}` 
            }
        })
        .then(res => res.data)
        .catch(error => error.response.data.errorMessage);
    return req;
}

export const SearchForABook = async (title,isbn) => {

    //GET dont have Body at the middle of the func
    const req = await userAxios('book').get('books/get/book/title-isbn',
        {
            params: { title: title, isbn: isbn },
            headers: {
                'Authorization': `${localStorage.jwtToken}` 
            }
        })
        .then(res => res.data)
        .catch(error => error.response.data.errorMessage);
    return req;
}

export const SearchBookAsResult = async (searchResult) => {

    //GET dont have Body at the middle of the func
    const req = await userAxios('book').get('books/get/book/title-author-isbn',
        {
            params: { searchResult: searchResult },
            headers: {
                'Authorization': `${localStorage.jwtToken}` 
            }
        })
        .then(res => res.data)
        .catch(error => error.response.data.errorMessage);
    return req;
}

export const SearchBookWithSelectedOptions = async (searchResult, options) => {

    let finalReq = [];
    let titleReq = [];
    let authorReq = [];
    let isbnReq = [];

    if(options.title) {
        //GET Books by Title
        titleReq = await userAxios('book').get('books/get/books/title',
        {
            params: { title: searchResult },
            headers: {
                'Authorization': `${localStorage.jwtToken}` 
            }
        })
        .then(res => res.data)
        .catch(error => error.response.data.errorMessage);
    }

    if(titleReq !== "Books not found. This title is invalid or this title is wrong") {
        finalReq = [...finalReq,titleReq];
    };
    
    if(options.author) {
        //GET Books by Author
        authorReq = await userAxios('book').get('books/get/books/author',
            {
                params: { author: searchResult },
                headers: {
                    'Authorization': `${localStorage.jwtToken}` 
                }
            })
            .then(res => res.data)
            .catch(error => error.response.data.errorMessage);
    }

    if(authorReq !== "Books not found. The author is invalid or the author name is wrong") {
        finalReq = [...finalReq,authorReq];
    };
    
    if(options.isbn) {
        //GET Book by ISBN
        isbnReq = await userAxios('book').get('books/get/book/isbn',
            {
                params: { isbn: searchResult },
                headers: {
                    'Authorization': `${localStorage.jwtToken}` 
                }
            })
            .then(res => res.data)
            .catch(error => error.response.data.errorMessage);
    }

    if(isbnReq !== "Book not found") {
        finalReq = [...finalReq,isbnReq];
    };

    return finalReq;
}

const fetcher = (url) => userAxios('book')
.get(url, { headers: { Authorization: `${localStorage.jwtToken}` } })
.then((res) => res.data);

export const GetAllBooks = () => {
    const { data, error } = useSWR('books/get/all', fetcher)
  
    return {
      data: data,
      isLoading: !error && !data,
      isError: error
    }
}

const getBookByIdFetcher = (url) => userAxios('book')
.get(url, { headers: { Authorization: `${localStorage.jwtToken}` } })
.then((res) => res.data);

export const GetBookById = (bookId) => {
    const { data, error } = useSWR('books/get/book/id/'+ bookId, getBookByIdFetcher)
  
    return {
      bookData: data,
      isLoading: !error && !data,
      isError: error
    }
}

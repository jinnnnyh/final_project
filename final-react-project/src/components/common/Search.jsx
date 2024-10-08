
function Search () {
  return (
  <div className={'input-group justify-content-end mb-4'}>
    <div className={'form-outline'}>
      <label className={'form-label hidden'} htmlFor={'searchForm'}>Search</label>
      <input type="search" id="searchForm" className={'form-control'} placeholder={'검색어를 입력해 주세요.'}/>
    </div>
    <button type="submit" className={'btn btn-secondary position-relative'}>
      <small className={'searchIco ms-4'}>검색</small>
    </button>
  </div>
  )
}

export default Search;

import Member from "../../pages/Member.jsx";


function MemberList () {
  return (
    <section>
      <Member/>
      <div className={'col-10'}>
        {/* 검색창 */}
        <div className="input-group justify-content-end mb-4">
          <div className="form-outline">
            <label className={'form-label hidden'} htmlFor={'searchForm'}>Search</label>
            <input type="search" id="searchForm" className={'form-control'} placeholder={'검색어를 입력해 주세요.'}/>
          </div>
          <button type="submit" className={'btn btn-secondary position-relative'}>
            <small className={'searchIco ms-4'}>검색</small>
          </button>
        </div>

        <table className={'table table-custom'}>
          <colgroup>
            <col width={"7%"}/>
            <col width={"7%"}/>
            <col width={"auto"}/>
            <col width={"15%"}/>
            <col width={"20%"}/>
            <col width={"15%"}/>
            <col width={"15%"}/>
          </colgroup>
          <thead>
          <tr>
            <th scope={'col'}></th>
            <th scope={'col'}>번호</th>
            <th scope={'col'}>이름</th>
            <th scope={'col'}>전화번호</th>
            <th scope={'col'}>이메일</th>
            <th scope={'col'}>회사명</th>
            <th scope={'col'}>직위</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>
              <div className="form-check">
                <input className="form-check-input" type="checkbox" value="" id="checkbox"/>
                <label className="form-check-label" htmlFor="checkbox"></label>
              </div>
            </td>
            <td>1</td>
            <td>아이유</td>
            <td>010-1111-1111</td>
            <td>iu@naver.com</td>
            <td>부산시청교육협회</td>
            <td>총무</td>
          </tr>
          <tr>
            <td>
              <div className="form-check">
                <input className="form-check-input" type="checkbox" value="" id="checkbox"/>
                <label className="form-check-label" htmlFor="checkbox"></label>
              </div>
            </td>
            <td>2</td>
            <td>아이유</td>
            <td>010-1111-1111</td>
            <td>iu@naver.com</td>
            <td>부산시청교육협회</td>
            <td>협회장</td>
          </tr>
          <tr>
            <td>
              <div className="form-check">
                <input className="form-check-input" type="checkbox" value="" id="checkbox"/>
                <label className="form-check-label" htmlFor="checkbox"></label>
              </div>
            </td>
            <td>3</td>
            <td>아이유</td>
            <td>010-1111-1111</td>
            <td>iu@naver.com</td>
            <td>부산시청교육협회</td>
            <td>일반회원</td>
          </tr>
          </tbody>
        </table>


        <div className={'d-flex justify-content-between mt-5'}>
          <div className={'justify-content-start'}>
            <button type={'submit'} className={'btn btn-outline-danger'}>삭제</button>
          </div>
          <div className={'justify-content-end'}>
            <button type={'submit'} className={'btn btn-outline-point me-2'}>수정</button>
            <button type={'submit'} className={'btn btn-point'}>등록</button>
          </div>
        </div>
      </div>

    </section>
  )
}

export default MemberList;
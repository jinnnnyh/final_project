import Member from "../../pages/Member.jsx";

function MemberList () {
  return (
    <section>
      <Member/>
      <div className={'form-border'}>
        <form>
          <div className={'d-flex mt-4 justify-content-between'}>
            <div className={'w-50 me-4'}>
              <label htmlFor={'user-name'} className={'form-label'}>아이디</label>
              <input type={'text'} className={'form-control py-2'} placeholder={'아이디를 입력해주세요'} id={'user-name'}/>
            </div>
            <div className={'w-50'}>
              <label htmlFor={'user-pw'} className={'form-label'}>비밀번호</label>
              <input type={'password'} className={'form-control py-2'} placeholder={'비밀번호를 입력해주세요'} id={'user-pw'}/>
            </div>
          </div>

          <div className={'d-flex mt-4 justify-content-between'}>
            <div className={'w-50 me-4'}>
              <label htmlFor={'user-name'} className={'form-label'}>이름</label>
              <input type={'text'} className={'form-control py-2'} placeholder={'이름을 입력해주세요'} id={'user-name'}/>
            </div>
            <div className={'w-50'}>
              <label htmlFor={'user-phone'} className={'form-label'}>전화번호</label>
              <input type={'text'} className={'form-control py-2'} placeholder={'전화번호를 입력해주세요'} id={'user-phone'}/>
            </div>
          </div>

          {/*<div className="mt-4">*/}
          {/*  <label htmlFor={'email'} className={'form-label'}>이메일</label>*/}
          {/*  <input type={'text'} className={'form-control py-3'} placeholder={'이메일을 입력해주세요'} id={'email'}/>*/}
          {/*</div>*/}

          <div className={'d-flex mt-4'}>
            <div className={'w-50 me-4'}>
              <label htmlFor={'user-depart'} className={'form-label'}>소속기관</label>
              <input type={'text'} className={'form-control py-2'} placeholder={'소속기관을 입력해주세요'} id={'user-depart'}/>
            </div>
            <div className={'w-50 '}>
              <label htmlFor={'user-role'} className={'form-label'}>직위</label>
              <select className={'form-select'} id={'user-role'} >
                <option selected>직위를 선택해주세요.</option>
                <option value="1">협회장</option>
                <option value="2">총무</option>
                <option value="3">일반회원</option>
              </select>
            </div>
          </div>

          {/* 버튼 */}
          <div className={'d-flex justify-content-end mt-5'}>
            <button type={'submit'} className={'btn btn-outline-point me-2'}>취소</button>
            <button type={'submit'} className={'btn btn-point'}>완료</button>
          </div>
        </form>
      </div>
    </section>
  )
}

export default MemberList;